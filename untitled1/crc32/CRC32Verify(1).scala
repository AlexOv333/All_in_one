package crc32

import crc32.CRC32Count

object CRC32Verify {
  def verifyCRC32(bytes: Array[Byte], expectedCRC: Long): Boolean = {
    CRC32Count.calculateCRC32(bytes) == expectedCRC
  }
}