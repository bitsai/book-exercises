#!/usr/bin/env ruby

require "blender"
require "test/unit"

class TestBlender < Test::Unit::TestCase
  def setup
    @blender = Blender.new
  end
  
  def test_full_run
    assert_nothing_raised do
      @blender.fill
      (1..9).to_a.each do |speed| @blender.setSpeed(speed) end
      (0..8).to_a.reverse.each do |speed| @blender.setSpeed(speed) end
      @blender.empty
    end
  end

  def test_speed_too_high
    assert_raise(RuntimeError) do
      @blender.fill
      @blender.setSpeed(5)
    end
  end

  def test_run_empty_blender_1
    assert_raise(RuntimeError) do
      @blender.setSpeed(1)
    end
  end

  def test_run_empty_blender_2
    assert_raise(RuntimeError) do
      @blender.fill
      @blender.setSpeed(1)
      @blender.setSpeed(0)
      @blender.empty
      @blender.setSpeed(1)
    end
  end

  def test_skip_speed
    assert_raise(RuntimeError) do
      @blender.fill
      @blender.setSpeed(1)
      @blender.setSpeed(2)
      @blender.setSpeed(3)
      @blender.setSpeed(8)
    end
  end

  def test_never_turn_on
    assert_nothing_raised do
      @blender.fill
      @blender.empty
    end
  end

  def test_empty_while_running
    assert_raise(RuntimeError) do
      @blender.fill
      @blender.setSpeed(1)
      @blender.empty
    end
  end

  def test_should_be_ok
    assert_nothing_raised do
      @blender.fill
      @blender.setSpeed(1)
      @blender.setSpeed(0)
      @blender.empty
    end
  end
end
