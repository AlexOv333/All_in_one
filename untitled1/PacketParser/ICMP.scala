package PacketParser
import messageOfICMP.typeOfMessage

object ICMP {
  def parserICMP(bytes: Seq[Byte]): Unit = {
    val typeOfMessage = bytes(0)
    typeOfMessage match
      case 8 => messageOfICMP.typeOfMessage.echoPingRequest(bytes)
      case 0 => messageOfICMP.typeOfMessage.echoPingReply(bytes)
      case 3 => messageOfICMP.typeOfMessage.destinationHostUnreacheble(bytes)
      case 11 => messageOfICMP.typeOfMessage.timeExcedeedMessage(bytes)
      case 12 => messageOfICMP.typeOfMessage.parameterProblemMessage(bytes)
      case 4 => messageOfICMP.typeOfMessage.sourceQuenchMessage(bytes)
      case 5 => messageOfICMP.typeOfMessage.redirectMessage(bytes)
      case 13 => messageOfICMP.typeOfMessage.timestampReplyMessage(bytes) // вот здесь еще есть case 14, но он идентичен 13, поэтому подумать как это лучше сделать
      case 15 => messageOfICMP.typeOfMessage.informationReplyMessage(bytes) // и здесь есть 16 тип
  }
}
