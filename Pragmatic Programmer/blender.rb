#!/usr/bin/env ruby

class Blender
  def initialize
    @speed = 0
    @full = false
  end

  def getSpeed 
    return @speed 
  end
    
  def setSpeed(new_speed)
    if !@full and new_speed > 0 then raise "Can't run empty blender" end
    if (@speed - new_speed).abs > 1  then raise "Can only change speed by one" end
    if !(0..9).include?(new_speed) then raise "Invaid speed" end

    @speed = new_speed 
  end
  
  def isFull
    return @full
  end

  def fill
    if isFull then raise "Can't fill blender twice" end

    @full = true
  end

  def empty
    if !isFull then raise "Can't empty blender twice" end
    if @speed > 0 then raise "Can't empty running blender" end
    
    @full = false
  end
end
