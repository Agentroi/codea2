import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester

class RegisterFileTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  poke(dut.io.rt,0)
  poke(dut.io.rd,5)
  poke(dut.io.r,1)
  poke(dut.io.write, false)
  poke(dut.io.data, 10)
  step(1)

  poke(dut.io.rt,0)
  poke(dut.io.rd,5)
  poke(dut.io.r,1)
  poke(dut.io.write, true)
  poke(dut.io.data, 10)
  step(1)

  poke(dut.io.rt,1)
  poke(dut.io.rd,5)
  poke(dut.io.r,2)
  poke(dut.io.write, true)
  poke(dut.io.data, 799)
  step(1)

  poke(dut.io.rt,0)
  poke(dut.io.rd,5)
  poke(dut.io.r,1)
  poke(dut.io.write, false)
  poke(dut.io.data, 10)
  step(1)


}

object RegisterFileTester {
  def main(args: Array[String]): Unit = {
    println("Testing RegisterFile")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterFileTester(c)
    }
  }
}
