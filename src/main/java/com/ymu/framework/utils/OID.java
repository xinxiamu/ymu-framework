package com.ymu.framework.utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

/**
 * OID生成器，比UUID更高效
 *
 */
public final class OID implements Comparable<OID>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8364876549535953016L;

	static final Logger logger = LoggerFactory.getLogger(OID.class);

	private static final int LOW_ORDER_FOUR_BYTES = 0xffffffff;

	private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;

	private static final int LOW_ORDER_TWO_BYTES = 0x0000ffff;

	private static final int MACHINE_IDENTIFIER;
	private static final short PROCESS_IDENTIFIER;
	private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

	private final long timestamp;
	private final int machineIdentifier;
	private final short processIdentifier;
	private final int counter;

	public static OID get() {
		return new OID();
	}

	public static int getGeneratedMachineIdentifier() {
		return MACHINE_IDENTIFIER;
	}

	public static int getGeneratedProcessIdentifier() {
		return PROCESS_IDENTIFIER;
	}

	public static int getCurrentCounter() {
		return NEXT_COUNTER.get();
	}

	public OID() {
		this(new Date());
	}

	public OID(final Date date) {
		this(dateToTimestamp(date), MACHINE_IDENTIFIER, PROCESS_IDENTIFIER, NEXT_COUNTER.getAndIncrement(), false);
	}

	public OID(final Date date, final int counter) {
		this(date, MACHINE_IDENTIFIER, PROCESS_IDENTIFIER, counter);
	}

	public OID(final Date date, final int machineIdentifier, final short processIdentifier, final int counter) {
		this(dateToTimestamp(date), machineIdentifier, processIdentifier, counter);
	}

	public OID(final long timestamp, final int machineIdentifier, final short processIdentifier, final int counter) {
		this(timestamp, machineIdentifier, processIdentifier, counter, true);
	}

	private OID(final long timestamp, final int machineIdentifier, final short processIdentifier, final int counter,
			final boolean checkCounter) {
		if ((machineIdentifier & 0xff000000) != 0) {
			throw new IllegalArgumentException(
					"The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
		}
		if (checkCounter && ((counter & 0xffff0000) != 0)) {
			throw new IllegalArgumentException("The counter must be between 0 and 65535 (it must fit in two bytes).");
		}
		this.timestamp = timestamp;
		this.machineIdentifier = machineIdentifier;
		this.processIdentifier = processIdentifier;
		this.counter = counter & LOW_ORDER_TWO_BYTES;
	}

	public OID(final String base64String) {
		this(parseBase64String(base64String));
	}

	public OID(final byte[] bytes) {
		this(ByteBuffer.wrap(bytes));
	}

	public OID(final ByteBuffer buffer) {
		if (buffer == null) {
			throw new NullPointerException("buffer不能为null");
		}
		Assert.isTrue(buffer.remaining() >= 12, "buffer.remaining() >= 12 is not true");
		timestamp = Longs.fromBytes((byte) 0, (byte) 0, (byte) 0, buffer.get(), buffer.get(), buffer.get(),
				buffer.get(), buffer.get());
		machineIdentifier = Ints.fromBytes((byte) 0, buffer.get(), buffer.get(), buffer.get());
		processIdentifier = Shorts.fromBytes(buffer.get(), buffer.get());
		counter = Shorts.fromBytes(buffer.get(), buffer.get());
	}

	public byte[] toByteArray() {
		byte[] bytes = new byte[12];
		System.arraycopy(Longs.toByteArray(timestamp), 3, bytes, 0, 5);
		System.arraycopy(Ints.toByteArray(machineIdentifier), 1, bytes, 5, 3);
		System.arraycopy(Shorts.toByteArray(processIdentifier), 0, bytes, 8, 2);
		System.arraycopy(Ints.toByteArray(counter), 2, bytes, 10, 2);
		return bytes;
	}

	/**
	 * 生成Long型的序列，此方法是不够安全的，存在较大几率的机器和环境冲突，且每秒只能生成最多6万个左右的序列
	 * 
	 * @return long型序列
	 */
	public long toLong() {
		byte[] bytes = new byte[8];
		System.arraycopy(Longs.toByteArray(timestamp >> 8 & LOW_ORDER_FOUR_BYTES), 4, bytes, 0, 4);
		String env = String.valueOf(machineIdentifier).concat("@").concat(String.valueOf(processIdentifier));
		System.arraycopy(Ints.toByteArray(env.hashCode() >> 8 & LOW_ORDER_TWO_BYTES), 2, bytes, 4, 2);
		System.arraycopy(Ints.toByteArray(counter), 2, bytes, 6, 2);
		return Longs.fromByteArray(bytes);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getMachineIdentifier() {
		return machineIdentifier;
	}

	public short getProcessIdentifier() {
		return processIdentifier;
	}

	public int getCounter() {
		return counter;
	}

	public Date getDate() {
		return new Date(timestamp * 1000L);
	}

	/**
	 * 转换为Base64编码
	 *
	 * @return
	 */
	public String toBase64UrlString() {
		Base64.Encoder encoder = Base64.getUrlEncoder();
		return encoder.encodeToString(toByteArray());
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		OID OID = (OID) o;

		if (counter != OID.counter) {
			return false;
		}
		if (machineIdentifier != OID.machineIdentifier) {
			return false;
		}
		if (processIdentifier != OID.processIdentifier) {
			return false;
		}
		if (timestamp != OID.timestamp) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = Long.valueOf(timestamp).hashCode();
		result = 31 * result + machineIdentifier;
		result = 31 * result + (int) processIdentifier;
		result = 31 * result + counter;
		return result;
	}

	@Override
	public int compareTo(final OID other) {
		if (other == null) {
			throw new NullPointerException();
		}

		byte[] byteArray = toByteArray();
		byte[] otherByteArray = other.toByteArray();
		for (int i = 0; i < 12; i++) {
			if (byteArray[i] != otherByteArray[i]) {
				return ((byteArray[i] & 0xff) < (otherByteArray[i] & 0xff)) ? -1 : 1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return toBase64UrlString();
	}

	public BigInteger toBigInteger() {
		return new BigInteger(toByteArray());
	}

	static {
		try {
			MACHINE_IDENTIFIER = createMachineIdentifier();
			PROCESS_IDENTIFIER = createProcessIdentifier();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static int createMachineIdentifier() {
		int machinePiece;
		try {
			StringBuilder sb = new StringBuilder();
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface ni = e.nextElement();
				sb.append(ni.toString());
				byte[] mac = ni.getHardwareAddress();
				if (mac != null) {
					ByteBuffer bb = ByteBuffer.wrap(mac);
					try {
						sb.append(bb.getChar());
						sb.append(bb.getChar());
						sb.append(bb.getChar());
					} catch (BufferUnderflowException shortHardwareAddressException) {
					}
				}
			}
			machinePiece = sb.toString().hashCode();
		} catch (Throwable t) {
			machinePiece = (new SecureRandom().nextInt());
			logger.warn("Failed to get machine identifier from network interface, using random number instead", t);
		}
		machinePiece = machinePiece & LOW_ORDER_THREE_BYTES;
		return machinePiece;
	}

	private static short createProcessIdentifier() {
		short processId;
		try {
			String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
			if (processName.contains("@")) {
				processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
			} else {
				processId = (short) java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
			}

		} catch (Throwable t) {
			processId = (short) new SecureRandom().nextInt();
			logger.warn("Failed to get process identifier from JMX, using random number instead", t);
		}
		return processId;
	}

	private static byte[] parseBase64String(final String s) {
		return Base64.getUrlDecoder().decode(s);
	}

	private static long dateToTimestamp(final Date time) {
		return time.getTime() / 4;
	}

}