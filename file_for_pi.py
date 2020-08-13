# coding=utf8
# -*- coding: utf-8 -*-
#!/usr/bin/python3
import time
from neopixel import *
import argparse
import RPi.GPIO as GPIO
import time
import urllib
#from xml.elementtree.Elementtree import Element, ElementTree, SubElement, dump, parse, tostring
import xml.etree.ElementTree as ET
import json
import socket
from collections import OrderedDict
from pprint import pprint
import schedule
import time
import datetime
import sys
reload(sys)
sys.setdefaultencoding('utf-8')


# LED strip configuration:
LED_COUNT      = 60      # Number of LED pixels.
LED_PIN        = 18      # GPIO pin connected to the pixels (18 uses PWM!).
#LED_PIN        = 10      # GPIO pin connected to the pixels (10 uses SPI /dev/spidev0.0).
LED_FREQ_HZ    = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA        = 10      # DMA channel to use for generating signal (try 10)
LED_BRIGHTNESS = 255     # Set to 0 for darkest and 255 for brightest
LED_INVERT     = False   # True to invert the signal (when using NPN transistor level shift)
LED_CHANNEL    = 0       # set to '1' for GPIOs 13, 19, 41, 45 or 53

pm10value = 0
pm25value = 0

# Color Here

normal_user_set1_red = 0
normal_user_set1_green = 0
normal_user_set1_blue = 0

normal_user_set2_red = 0
normal_user_set2_green = 0
normal_user_set2_blue = 0

normal_user_set3_red = 0
normal_user_set3_green =0
normal_user_set3_blue = 0

normal_user_set4_red = 0
normal_user_set4_green =0
normal_user_set4_blue = 0

normal_user_set5_red = 0
normal_user_set5_green =0
normal_user_set5_blue = 0

pm10_user_set1_1_red = 0
pm10_user_set1_1_green = 0
pm10_user_set1_1_blue = 0
pm10_user_set1_2_red = 0
pm10_user_set1_2_green = 0
pm10_user_set1_2_blue = 0
pm10_user_set1_3_red = 0
pm10_user_set1_3_green = 0
pm10_user_set1_3_blue = 0
pm10_user_set1_4_red = 0
pm10_user_set1_4_green = 0
pm10_user_set1_4_blue = 0
pm10_user_set1_5_red = 0
pm10_user_set1_5_green = 0
pm10_user_set1_5_blue = 0
pm10_user_set1_6_red = 0
pm10_user_set1_6_green = 0
pm10_user_set1_6_blue = 0
pm10_user_set1_7_red = 0
pm10_user_set1_7_green = 0
pm10_user_set1_7_blue = 0
pm10_user_set1_8_red = 0
pm10_user_set1_8_green = 0
pm10_user_set1_8_blue = 0

pm10_user_set2_1_red = 0
pm10_user_set2_1_green = 0
pm10_user_set2_1_blue = 0
pm10_user_set2_2_red = 0
pm10_user_set2_2_green = 0
pm10_user_set2_2_blue = 0
pm10_user_set2_3_red = 0
pm10_user_set2_3_green = 0
pm10_user_set2_3_blue = 0
pm10_user_set2_4_red = 0
pm10_user_set2_4_green = 0
pm10_user_set2_4_blue = 0
pm10_user_set2_5_red = 0
pm10_user_set2_5_green = 0
pm10_user_set2_5_blue = 0
pm10_user_set2_6_red = 0
pm10_user_set2_6_green = 0
pm10_user_set2_6_blue = 0
pm10_user_set2_7_red = 0
pm10_user_set2_7_green = 0
pm10_user_set2_7_blue = 0
pm10_user_set2_8_red = 0
pm10_user_set2_8_green = 0
pm10_user_set2_8_blue = 0

pm25_user_set1_1_red = 0
pm25_user_set1_1_green = 0
pm25_user_set1_1_blue = 0
pm25_user_set1_2_red = 0
pm25_user_set1_2_green = 0
pm25_user_set1_2_blue = 0
pm25_user_set1_3_red = 0
pm25_user_set1_3_green = 0
pm25_user_set1_3_blue = 0
pm25_user_set1_4_red = 0
pm25_user_set1_4_green = 0
pm25_user_set1_4_blue = 0
pm25_user_set1_5_red = 0
pm25_user_set1_5_green = 0
pm25_user_set1_5_blue = 0
pm25_user_set1_6_red = 0
pm25_user_set1_6_green = 0
pm25_user_set1_6_blue = 0
pm25_user_set1_7_red = 0
pm25_user_set1_7_green = 0
pm25_user_set1_7_blue = 0
pm25_user_set1_8_red = 0
pm25_user_set1_8_green = 0
pm25_user_set1_8_blue = 0

pm25_user_set2_1_red = 0
pm25_user_set2_1_green = 0
pm25_user_set2_1_blue = 0
pm25_user_set2_2_red = 0
pm25_user_set2_2_green = 0
pm25_user_set2_2_blue = 0
pm25_user_set2_3_red = 0
pm25_user_set2_3_green = 0
pm25_user_set2_3_blue = 0
pm25_user_set2_4_red = 0
pm25_user_set2_4_green = 0
pm25_user_set2_4_blue = 0
pm25_user_set2_5_red = 0
pm25_user_set2_5_green = 0
pm25_user_set2_5_blue = 0
pm25_user_set2_6_red = 0
pm25_user_set2_6_green = 0
pm25_user_set2_6_blue = 0
pm25_user_set2_7_red = 0
pm25_user_set2_7_green = 0
pm25_user_set2_7_blue = 0
pm25_user_set2_8_red = 0
pm25_user_set2_8_green = 0
pm25_user_set2_8_blue = 0

 
# Define functions which animate LEDs in various ways.
setup = 0

def ExitTCP():
	result = "exit,0"
	return result

def colorWipe(strip, color, wait_ms=50):
    """Wipe color across display a pixel at a time."""
    for i in range(strip.numPixels()):
        strip.setPixelColor(i, color)
        strip.show()
        time.sleep(wait_ms/1000.0)
 
# Define functions which animate LEDs in various ways.
def colorSimple(strip, color, wait_ms=50):
	"""Wipe color across display a pixel at a time."""
	for i in range(strip.numPixels()):
		strip.setPixelColor(i, color)
		strip.show()
		time.sleep(wait_ms/1000.0)

# Control LED Here
def ControlLED(input_string):
	input_string = ("LED,"+str(pm10_user_set1_1_red)+","+str(pm10_user_set1_1_blue)+","+str(pm10_user_set1_1_green)+","
				+str(pm10_user_set1_2_red)+","+str(pm10_user_set1_2_blue)+","+str(pm10_user_set1_2_green)+","
				+str(pm10_user_set1_3_red)+","+str(pm10_user_set1_3_blue)+","+str(pm10_user_set1_3_green)+","
				+str(pm10_user_set1_4_red)+","+str(pm10_user_set1_4_blue)+","+str(pm10_user_set1_4_green)+","
				+str(pm10_user_set1_5_red)+","+str(pm10_user_set1_5_blue)+","+str(pm10_user_set1_5_green)+","
				+str(pm10_user_set1_6_red)+","+str(pm10_user_set1_6_blue)+","+str(pm10_user_set1_6_green)+","
				+str(pm10_user_set1_7_red)+","+str(pm10_user_set1_7_blue)+","+str(pm10_user_set1_7_green)+","
				+str(pm10_user_set1_8_red)+","+str(pm10_user_set1_8_blue)+","+str(pm10_user_set1_8_green)+","
				+str(pm10_user_set2_1_red)+","+str(pm10_user_set2_1_blue)+","+str(pm10_user_set2_1_green)+","
				+str(pm10_user_set2_2_red)+","+str(pm10_user_set2_2_blue)+","+str(pm10_user_set2_2_green)+","
				+str(pm10_user_set2_3_red)+","+str(pm10_user_set2_3_blue)+","+str(pm10_user_set2_3_green)+","
				+str(pm10_user_set2_4_red)+","+str(pm10_user_set2_4_blue)+","+str(pm10_user_set2_4_green)+","
				+str(pm10_user_set2_5_red)+","+str(pm10_user_set2_5_blue)+","+str(pm10_user_set2_5_green)+","
				+str(pm10_user_set2_6_red)+","+str(pm10_user_set2_6_blue)+","+str(pm10_user_set2_6_green)+","
				+str(pm10_user_set2_7_red)+","+str(pm10_user_set2_7_blue)+","+str(pm10_user_set2_7_green)+","
				+str(pm10_user_set2_8_red)+","+str(pm10_user_set2_8_blue)+","+str(pm10_user_set2_8_green)+","
				+str(pm25_user_set1_1_red)+","+str(pm25_user_set1_1_blue)+","+str(pm25_user_set1_1_green)+","
				+str(pm25_user_set1_2_red)+","+str(pm25_user_set1_2_blue)+","+str(pm25_user_set1_2_green)+","
				+str(pm25_user_set1_3_red)+","+str(pm25_user_set1_3_blue)+","+str(pm25_user_set1_3_green)+","
				+str(pm25_user_set1_4_red)+","+str(pm25_user_set1_4_blue)+","+str(pm25_user_set1_4_green)+","
				+str(pm25_user_set1_5_red)+","+str(pm25_user_set1_5_blue)+","+str(pm25_user_set1_5_green)+","
				+str(pm25_user_set1_6_red)+","+str(pm25_user_set1_6_blue)+","+str(pm25_user_set1_6_green)+","
				+str(pm25_user_set1_7_red)+","+str(pm25_user_set1_7_blue)+","+str(pm25_user_set1_7_green)+","
				+str(pm25_user_set1_8_red)+","+str(pm25_user_set1_8_blue)+","+str(pm25_user_set1_8_green)+","
				+str(pm25_user_set2_1_red)+","+str(pm25_user_set2_1_blue)+","+str(pm25_user_set2_1_green)+","
				+str(pm25_user_set2_2_red)+","+str(pm25_user_set2_2_blue)+","+str(pm25_user_set2_2_green)+","
				+str(pm25_user_set2_3_red)+","+str(pm25_user_set2_3_blue)+","+str(pm25_user_set2_3_green)+","
				+str(pm25_user_set2_4_red)+","+str(pm25_user_set2_4_blue)+","+str(pm25_user_set2_4_green)+","
				+str(pm25_user_set2_5_red)+","+str(pm25_user_set2_5_blue)+","+str(pm25_user_set2_5_green)+","
				+str(pm25_user_set2_6_red)+","+str(pm25_user_set2_6_blue)+","+str(pm25_user_set2_6_green)+","
				+str(pm25_user_set2_7_red)+","+str(pm25_user_set2_7_blue)+","+str(pm25_user_set2_7_green)+","
				+str(pm25_user_set2_8_red)+","+str(pm25_user_set2_8_blue)+","+str(pm25_user_set2_8_green)+","
				+str(normal_user_set1_red)+","+str(normal_user_set1_blue)+","+str(normal_user_set1_green)+","
				+str(normal_user_set2_red)+","+str(normal_user_set2_blue)+","+str(normal_user_set2_green)+","
				+str(normal_user_set3_red)+","+str(normal_user_set3_blue)+","+str(normal_user_set3_green)+","
				+str(normal_user_set4_red)+","+str(normal_user_set4_blue)+","+str(normal_user_set4_green)+","
				+str(normal_user_set5_red)+","+str(normal_user_set5_blue)+","+str(normal_user_set5_green))
	return input_string

def SetupLED(data):
	global normal_user_set1_red
	global normal_user_set1_green
	global normal_user_set1_blue

	global normal_user_set2_red
	global normal_user_set2_green
	global normal_user_set2_blue

	global normal_user_set3_red
	global normal_user_set3_green
	global normal_user_set3_blue

	global normal_user_set4_red
	global normal_user_set4_green
	global normal_user_set4_blue

	global normal_user_set5_red
	global normal_user_set5_green
	global normal_user_set5_blue

	global pm10_user_set1_1_red
	global pm10_user_set1_1_green
	global pm10_user_set1_1_blue
	global pm10_user_set1_2_red
	global pm10_user_set1_2_green
	global pm10_user_set1_2_blue
	global pm10_user_set1_3_red
	global pm10_user_set1_3_green
	global pm10_user_set1_3_blue
	global pm10_user_set1_4_red
	global pm10_user_set1_4_green
	global pm10_user_set1_4_blue
	global pm10_user_set1_5_red
	global pm10_user_set1_5_green
	global pm10_user_set1_5_blue
	global pm10_user_set1_6_red
	global pm10_user_set1_6_green
	global pm10_user_set1_6_blue
	global pm10_user_set1_7_red
	global pm10_user_set1_7_green
	global pm10_user_set1_7_blue
	global pm10_user_set1_8_red
	global pm10_user_set1_8_green
	global pm10_user_set1_8_blue

	global pm10_user_set2_1_red
	global pm10_user_set2_1_green
	global pm10_user_set2_1_blue
	global pm10_user_set2_2_red
	global pm10_user_set2_2_green
	global pm10_user_set2_2_blue
	global pm10_user_set2_3_red
	global pm10_user_set2_3_green
	global pm10_user_set2_3_blue
	global pm10_user_set2_4_red
	global pm10_user_set2_4_green
	global pm10_user_set2_4_blue
	global pm10_user_set2_5_red
	global pm10_user_set2_5_green
	global pm10_user_set2_5_blue
	global pm10_user_set2_6_red
	global pm10_user_set2_6_green
	global pm10_user_set2_6_blue
	global pm10_user_set2_7_red
	global pm10_user_set2_7_green
	global pm10_user_set2_7_blue
	global pm10_user_set2_8_red
	global pm10_user_set2_8_green
	global pm10_user_set2_8_blue

	global pm25_user_set1_1_red
	global pm25_user_set1_1_green
	global pm25_user_set1_1_blue
	global pm25_user_set1_2_red
	global pm25_user_set1_2_green
	global pm25_user_set1_2_blue
	global pm25_user_set1_3_red
	global pm25_user_set1_3_green
	global pm25_user_set1_3_blue
	global pm25_user_set1_4_red
	global pm25_user_set1_4_green
	global pm25_user_set1_4_blue
	global pm25_user_set1_5_red
	global pm25_user_set1_5_green
	global pm25_user_set1_5_blue
	global pm25_user_set1_6_red
	global pm25_user_set1_6_green
	global pm25_user_set1_6_blue
	global pm25_user_set1_7_red
	global pm25_user_set1_7_green
	global pm25_user_set1_7_blue
	global pm25_user_set1_8_red
	global pm25_user_set1_8_green
	global pm25_user_set1_8_blue

	global pm25_user_set2_1_red
	global pm25_user_set2_1_green
	global pm25_user_set2_1_blue
	global pm25_user_set2_2_red
	global pm25_user_set2_2_green
	global pm25_user_set2_2_blue
	global pm25_user_set2_3_red
	global pm25_user_set2_3_green
	global pm25_user_set2_3_blue
	global pm25_user_set2_4_red
	global pm25_user_set2_4_green
	global pm25_user_set2_4_blue
	global pm25_user_set2_5_red
	global pm25_user_set2_5_green
	global pm25_user_set2_5_blue
	global pm25_user_set2_6_red
	global pm25_user_set2_6_green
	global pm25_user_set2_6_blue
	global pm25_user_set2_7_red
	global pm25_user_set2_7_green
	global pm25_user_set2_7_blue
	global pm25_user_set2_8_red
	global pm25_user_set2_8_green
	global pm25_user_set2_8_blue

	global stationname 
	global stationname2 
	global setup

	pm10_user_set1_8_red = int(data2[1])
	pm10_user_set1_8_green = int(data2[2])
	pm10_user_set1_8_blue = int(data2[3]) 
	pm10_user_set1_7_red = int(data2[4]) 
	pm10_user_set1_7_green = int(data2[5]) 
	pm10_user_set1_7_blue =int(data2[6])  
	pm10_user_set1_6_red =int(data2[7])  
	pm10_user_set1_6_green = int(data2[8]) 
	pm10_user_set1_6_blue = int(data2[9]) 
	pm10_user_set1_5_red = int(data2[10]) 
	pm10_user_set1_5_green = int(data2[11]) 
	pm10_user_set1_5_blue = int(data2[12]) 
	pm10_user_set1_4_red = int(data2[13]) 
	pm10_user_set1_4_green = int(data2[14]) 
	pm10_user_set1_4_blue = int(data2[15]) 
	pm10_user_set1_3_red = int(data2[16]) 
	pm10_user_set1_3_green = int(data2[17]) 
	pm10_user_set1_3_blue = int(data2[18]) 
	pm10_user_set1_2_red = int(data2[19]) 
	pm10_user_set1_2_green = int(data2[20]) 
	pm10_user_set1_2_blue = int(data2[21]) 
	pm10_user_set1_1_red = int(data2[22]) 
	pm10_user_set1_1_green = int(data2[23]) 
	pm10_user_set1_1_blue = int(data2[24]) 
	
	pm10_user_set2_8_red = int(data2[25])
	pm10_user_set2_8_green = int(data2[26])
	pm10_user_set2_8_blue = int(data2[27]) 
	pm10_user_set2_7_red = int(data2[28]) 
	pm10_user_set2_7_green = int(data2[29]) 
	pm10_user_set2_7_blue =int(data2[30])  
	pm10_user_set2_6_red =int(data2[31])  
	pm10_user_set2_6_green = int(data2[32]) 
	pm10_user_set2_6_blue = int(data2[33]) 
	pm10_user_set2_5_red = int(data2[34]) 
	pm10_user_set2_5_green = int(data2[35]) 
	pm10_user_set2_5_blue = int(data2[36]) 
	pm10_user_set2_4_red = int(data2[37]) 
	pm10_user_set2_4_green = int(data2[38]) 
	pm10_user_set2_4_blue = int(data2[39]) 
	pm10_user_set2_3_red = int(data2[40]) 
	pm10_user_set2_3_green = int(data2[41]) 
	pm10_user_set2_3_blue = int(data2[42]) 
	pm10_user_set2_2_red = int(data2[43]) 
	pm10_user_set2_2_green = int(data2[44]) 
	pm10_user_set2_2_blue = int(data2[45]) 
	pm10_user_set2_1_red = int(data2[46]) 
	pm10_user_set2_1_green = int(data2[47]) 
	pm10_user_set2_1_blue = int(data2[48]) 
	
	pm25_user_set1_8_red = int(data2[49])
	pm25_user_set1_8_green = int(data2[50])
	pm25_user_set1_8_blue = int(data2[51]) 
	pm25_user_set1_7_red = int(data2[52]) 
	pm25_user_set1_7_green = int(data2[53]) 
	pm25_user_set1_7_blue =int(data2[54])  
	pm25_user_set1_6_red =int(data2[55])  
	pm25_user_set1_6_green = int(data2[56]) 
	pm25_user_set1_6_blue = int(data2[57]) 
	pm25_user_set1_5_red = int(data2[58]) 
	pm25_user_set1_5_green = int(data2[59]) 
	pm25_user_set1_5_blue = int(data2[60]) 
	pm25_user_set1_4_red = int(data2[61]) 
	pm25_user_set1_4_green = int(data2[62]) 
	pm25_user_set1_4_blue = int(data2[63]) 
	pm25_user_set1_3_red = int(data2[64]) 
	pm25_user_set1_3_green = int(data2[65]) 
	pm25_user_set1_3_blue = int(data2[66]) 
	pm25_user_set1_2_red = int(data2[67])
	pm25_user_set1_2_green = int(data2[68]) 
	pm25_user_set1_2_blue = int(data2[69]) 
	pm25_user_set1_1_red = int(data2[70]) 
	pm25_user_set1_1_green = int(data2[71]) 
	pm25_user_set1_1_blue = int(data2[72]) 
	
	pm25_user_set2_8_red = int(data2[73])
	pm25_user_set2_8_green = int(data2[74])
	pm25_user_set2_8_blue = int(data2[75]) 
	pm25_user_set2_7_red = int(data2[76]) 
	pm25_user_set2_7_green = int(data2[77]) 
	pm25_user_set2_7_blue =int(data2[78])  
	pm25_user_set2_6_red =int(data2[79])  
	pm25_user_set2_6_green = int(data2[80]) 
	pm25_user_set2_6_blue = int(data2[81]) 
	pm25_user_set2_5_red = int(data2[82]) 
	pm25_user_set2_5_green = int(data2[83]) 
	pm25_user_set2_5_blue = int(data2[84]) 
	pm25_user_set2_4_red = int(data2[85]) 
	pm25_user_set2_4_green = int(data2[86]) 
	pm25_user_set2_4_blue = int(data2[87]) 
	pm25_user_set2_3_red = int(data2[88]) 
	pm25_user_set2_3_green = int(data2[89]) 
	pm25_user_set2_3_blue = int(data2[90]) 
	pm25_user_set2_2_red = int(data2[91]) 
	pm25_user_set2_2_green = int(data2[92]) 
	pm25_user_set2_2_blue = int(data2[93]) 
	pm25_user_set2_1_red = int(data2[94]) 
	pm25_user_set2_1_green = int(data2[95]) 
	pm25_user_set2_1_blue = int(data2[96])
	normal_user_set1_red = int(data2[97])
	normal_user_set1_green = int(data2[98])
	normal_user_set1_blue = int(data2[99])
	normal_user_set2_red = int(data2[100])
	normal_user_set2_green = int(data2[101])
	normal_user_set2_blue = int(data2[102])
	normal_user_set3_red = int(data2[103])
	normal_user_set3_green = int(data2[104])
	normal_user_set3_blue = int(data2[105])
	normal_user_set4_red = int(data2[106])
	normal_user_set4_green = int(data2[107])
	normal_user_set4_blue = int(data2[108])
	normal_user_set5_red = int(data2[109])
	normal_user_set5_green = int(data2[110])
	normal_user_set5_blue = int(data2[111])
	stationname=data2[112]
	stationname2=data2[113]
	setup = 1
	res = "exit"
	return res
	
def execute_func(second=1800.0):
	global end
	if end:
		return

	print("data")

	threading.Timer(second, execute_func,[second]).start()
 
# Main program logic follows:
if __name__ == '__main__':

	#TCP Connection
	HOST = "192.168.0.103"
	PORT = 8888
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)							
	print ('Socket created')
	s.bind((HOST, PORT))
	print ('Socket bind complete')
	#color settings here
	
# Process arguments
	parser = argparse.ArgumentParser()
	parser.add_argument('-c', '--clear', action='store_true', help='clear the display on exit')
	args = parser.parse_args()

	GPIO.setmode(GPIO.BCM)
	GPIO.setup(27, GPIO.IN, pull_up_down=GPIO.PUD_UP)
	GPIO.setup(23, GPIO.IN, pull_up_down=GPIO.PUD_UP) 
	GPIO.setup(22, GPIO.IN, pull_up_down=GPIO.PUD_UP) 
	GPIO.setup(4, GPIO.IN, pull_up_down=GPIO.PUD_UP) 
	GPIO.setup(16, GPIO.OUT)
	GPIO.output(16, False)
	GPIO.setup(20, GPIO.OUT)
	GPIO.output(20, False)
	GPIO.setup(12, GPIO.OUT)
	GPIO.output(12, False)
	GPIO.setup(17, GPIO.OUT)
	GPIO.output(17, False)
	# Create NeoPixel object with appropriate configuration.
	strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS, LED_CHANNEL)
	strip.begin()
	value2 = 0
	value3 = 0
	value4 = 0
	value5 = 0
	prev_input = 0
	prev_onoff = 0
	prev_mode = 0
	prev_mode2 = 0
	prev_tcp = 0
	timecount = 0

	print ('Press Ctrl-C to quit.')
	if not args.clear:
		print('Use "-c" argument to clear LEDs on exit')
		req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
				"stationName=명서동"+
				"&dataTerm=month&pageNo=1&numOfRows=1"+
				"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
		print("data requested")
		data = req.read()
		jsonString = json.loads(data)
	try:
		while True:

			input_onoff = GPIO.input(23)
			if input_onoff != prev_onoff:
				if prev_onoff == False and input_onoff == True:
					value2 = value2 + 1

			input_mode = GPIO.input(22)
			if input_mode != prev_mode:
				if prev_mode == False and input_mode == True:
					timecount = 0
					value4 = 0
					value3 = value3 +1

			input_tcp = GPIO.input(27)

			if(value3 ==4):
				value3 =0
			if(value2 ==2):
				value2 =0

			if value2 %2 ==1:
				if value3 %4 ==0:
					input_mode2 = GPIO.input(4)
					if input_mode2 != prev_mode2:
						if prev_mode2 == False and input_mode2 == True:
							value4 = value4 +1

					print"Normalmode"
					GPIO.output(17,False)
					GPIO.output(16, True)
					GPIO.output(20, False)
					GPIO.output(12, False)
					print value4
					if value4==12:
						value4=0
					# Green, Red, Blue

					if(value4%12 == 0):
						colorSimple(strip,Color(0,255,0),10)
					elif(value4%12 == 1):
						colorSimple(strip,Color(127,255,0),10)
					elif(value4%12 == 2):
						colorSimple(strip,Color(255,255,0),10)
					elif(value4%12 == 3):
						colorSimple(strip,Color(255,0,0),10)
					elif(value4%12 == 4):
						colorSimple(strip,Color(normal_user_set1_green,normal_user_set1_red,normal_user_set1_blue),10)
					elif(value4%12 == 5):
						colorSimple(strip,Color(normal_user_set2_green,normal_user_set2_red,normal_user_set2_blue),10)
					elif(value4%12 == 6):
						colorSimple(strip,Color(normal_user_set3_green,normal_user_set3_red,normal_user_set3_blue),10)
					elif(value4%12 == 7):
						colorSimple(strip,Color(normal_user_set4_green,normal_user_set4_red,normal_user_set4_blue),10)
					elif(value4%12 == 8):
						colorSimple(strip,Color(normal_user_set5_green,normal_user_set5_red,normal_user_set5_blue),10)
					elif(value4%12 == 9):
						colorSimple(strip,Color(255,0,255),10)
					elif(value4%12 == 10):
						colorSimple(strip,Color(0,0,128),10)
					elif(value4%12 == 11):
						colorSimple(strip,Color(0,148,211),10)
				elif(value3%4==1):
					GPIO.output(17,False)
					GPIO.output(16, False)
					GPIO.output(20, True)
					GPIO.output(12, False)
					time.sleep(0.01)
					timecount = timecount  +1
					input_mode2 = GPIO.input(4)
					if value4 > 4:
						value4 = 0
					if input_mode2 != prev_mode2:
						if prev_mode2 == False and input_mode2 == True:
							value4 = value4 +1
					if timecount > 2540:
						timecount = 0
					if timecount%2540==3:
						if setup == 0:
							req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName=명서동"+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
						else:
							req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName="+str(unicode(stationname))+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
						print "data requested"
						print datetime.datetime.now()
						data = req.read()
						jsonString = json.loads(data)
						name = jsonString["list"]
						name2 = name[0]
						#input color mapping here
						pm10value=name2["pm10Value"]
						if pm10value == '-':
							if setup == 0:
								req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName=반송로"+
									"&dataTerm=month&pageNo=1&numOfRows=1"+
								"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
							else:
								req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
									"stationName="+str(unicode(stationname2))+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
							data = req.read()
							jsonString = json.loads(data)
							name = jsonString["list"]
							name2 = name[0]
							#input color mapping here
							pm10value=name2["pm10Value"]
					pm10value = int(pm10value)
					#print pm10value
					name = jsonString["list"]
					name2 = name[0]
					#input color mapping here
#						15/30/45/50/75/100/150
					if(value4%5==0):
						if(pm10value<15):
							colorSimple(strip,Color(0,255,255),10)
						elif(pm10value<30):
							colorSimple(strip,Color(0,255,165),10)
						elif(pm10value<45):
							colorSimple(strip,Color(0,255,110),10)
						elif(pm10value<50):
							colorSimple(strip,Color(80,255,0),10)
						elif(pm10value<75):
							colorSimple(strip,Color(140,255,0),10)
						elif(pm10value<100):
							colorSimple(strip,Color(216,255,0),10)
						elif(pm10value<150):
							colorSimple(strip,Color(255,212,0),10)
						else:
							colorSimple(strip,Color(255,0,33),10)
					elif(value4%5==1):
						if(pm10value<15):
							colorSimple(strip,Color(0,255,255),10)
						elif(pm10value<30):
							colorSimple(strip,Color(51,204,204),10)
						elif(pm10value<45):
							colorSimple(strip,Color(102,204,204),10)
						elif(pm10value<50):
							colorSimple(strip,Color(102,255,255),10)
						elif(pm10value<75):
							colorSimple(strip,Color(153,255,255),10)
						elif(pm10value<100):
							colorSimple(strip,Color(204,255,255),10)
						elif(pm10value<150):
							colorSimple(strip,Color(255,242,242),10)	
					elif(value4%5==2):	
						if(pm10value<15):
							colorSimple(strip,Color(255,0,242),10)							
						elif(pm10value<30):
							colorSimple(strip,Color(153,0,255),10)	
						elif(pm10value<45):
							colorSimple(strip,Color(114,0,255),10)	
						elif(pm10value<50):
							colorSimple(strip,Color(17,0,255),10)	
						elif(pm10value<75):
							colorSimple(strip,Color(0,127,255),10)	
						elif(pm10value<100):
							colorSimple(strip,Color(0,174,255),10)	
						elif(pm10value<150):
							colorSimple(strip,Color(0,255,255),10)	
					elif(value4%5==3):
						print pm10_user_set1_2_red
						print pm10_user_set1_2_green
						print pm10_user_set1_2_blue
						if(pm10value<15):
							colorSimple(strip,Color(pm10_user_set1_1_green,pm10_user_set1_1_red,pm10_user_set1_1_blue),10)
						elif(pm10value<30):
							print "right"
							colorSimple(strip,Color(pm10_user_set1_2_green,pm10_user_set1_2_red,pm10_user_set1_2_blue),10)
						elif(pm10value<45):
							colorSimple(strip,Color(pm10_user_set1_3_green,pm10_user_set1_3_red,pm10_user_set1_3_blue),10)
						elif(pm10value<50):
							colorSimple(strip,Color(pm10_user_set1_4_green,pm10_user_set1_4_red,pm10_user_set1_4_blue),10)
						elif(pm10value<75):
							colorSimple(strip,Color(pm10_user_set1_5_green,pm10_user_set1_5_red,pm10_user_set1_5_blue),10)
						elif(pm10value<100):
							colorSimple(strip,Color(pm10_user_set1_6_green,pm10_user_set1_6_red,pm10_user_set1_6_blue),10)
						elif(pm10value<150):
							colorSimple(strip,Color(pm10_user_set1_7_green,pm10_user_set1_7_red,pm10_user_set1_7_blue),10)
						else:
							colorSimple(strip,Color(pm10_user_set1_8_green,pm10_user_set1_8_red,pm10_user_set1_8_blue),10)
					elif(value4%5==4):
						if(pm10value<15):
							colorSimple(strip,Color(pm10_user_set2_1_green,pm10_user_set2_1_red,pm10_user_set2_1_blue),10)
						elif(pm10value<30):
							colorSimple(strip,Color(pm10_user_set2_2_green,pm10_user_set2_2_red,pm10_user_set2_2_blue),10)
						elif(pm10value<45):
							colorSimple(strip,Color(pm10_user_set2_3_green,pm10_user_set2_3_red,pm10_user_set2_3_blue),10)
						elif(pm10value<50):
							colorSimple(strip,Color(pm10_user_set2_4_green,pm10_user_set2_4_red,pm10_user_set2_4_blue),10)
						elif(pm10value<75):
							colorSimple(strip,Color(pm10_user_set2_5_green,pm10_user_set2_5_red,pm10_user_set2_5_blue),10)
						elif(pm10value<100):
							colorSimple(strip,Color(pm10_user_set2_6_green,pm10_user_set2_6_red,pm10_user_set2_6_blue),10)
						elif(pm10value<150):
							colorSimple(strip,Color(pm10_user_set2_7_green,pm10_user_set2_7_red,pm10_user_set2_7_blue),10)
						else:
							colorSimple(strip,Color(pm10_user_set2_8_green,pm10_user_set2_8_red,pm10_user_set2_8_blue),10)
				elif(value3%4==2):
					GPIO.output(17,False)
					GPIO.output(16, False)
					GPIO.output(20, False)
					GPIO.output(12, True)
					time.sleep(0.01)
					timecount = timecount  +1
					input_mode2 = GPIO.input(4)
					if value4 > 4:
						value4 = 0
					if input_mode2 != prev_mode2:
						if prev_mode2 == False and input_mode2 == True:
							value4 = value4 +1
					if timecount%2540==3:
						if setup == 0:
							req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName=명서동"+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
						else:
							print str(unicode(stationname))
							req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName="+str(unicode(stationname))+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
						print "data requested"
						print datetime.datetime.now()
						data = req.read()
						jsonString = json.loads(data)
						name = jsonString["list"]
						name2 = name[0]
						#input color mapping here
						pm25value=name2["pm25Value"]
						print setup
						if pm25value == "-":
							if setup == 0:
								req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
								"stationName=반송로"+
									"&dataTerm=month&pageNo=1&numOfRows=1"+
								"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
							else:
								req = urllib.urlopen("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"+
									"stationName="+str(unicode(stationname2))+
								"&dataTerm=month&pageNo=1&numOfRows=1"+
							"&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D&ver=1.3&_returnType=json");
							data = req.read()
							jsonString = json.loads(data)
							name = jsonString["list"]
							name2 = name[0]
							#input color mapping here
							pm25value=name2["pm25Value"]
					pm25value = int(pm25value)
					print pm25value
					#print timecount
					name = jsonString["list"]
					name2 = name[0]
					#print value4
					if(value4%5==0):
						if(pm25value<8):
							colorSimple(strip,Color(0,255,255),10)
						elif(pm25value<15):
							colorSimple(strip,Color(0,255,165),10)
						elif(pm25value<20):
							colorSimple(strip,Color(0,255,110),10)
						elif(pm25value<25):
							colorSimple(strip,Color(80,255,0),10)
						elif(pm25value<37):
							colorSimple(strip,Color(140,255,0),10)
						elif(pm25value<50):
							colorSimple(strip,Color(216,255,0),10)
						elif(pm25value<75):
							colorSimple(strip,Color(255,212,0),10)
						else:
							colorSimple(strip,Color(255,0,33),10)
					elif(value4%5==1):
						if(pm25value<8):
							colorSimple(strip,Color(0,255,255),10)
						elif(pm25value<15):
							colorSimple(strip,Color(51,204,204),10)
						elif(pm25value<20):
							colorSimple(strip,Color(102,204,204),10)
						elif(pm25value<25):
							colorSimple(strip,Color(102,255,255),10)
						elif(pm25value<37):
							colorSimple(strip,Color(153,255,255),10)
						elif(pm25value<50):
							colorSimple(strip,Color(204,255,255),10)
						elif(pm25value<75):
							colorSimple(strip,Color(255,242,242),10)	
					elif(value4%5==2):
						if(pm25value<8):
							colorSimple(strip,Color(255,0,242),10)							
						elif(pm25value<15):
							colorSimple(strip,Color(153,0,255),10)	
						elif(pm25value<20):
							colorSimple(strip,Color(114,0,255),10)	
						elif(pm25value<25):
							colorSimple(strip,Color(17,0,255),10)	
						elif(pm25value<37):
							colorSimple(strip,Color(0,127,255),10)	
						elif(pm25value<50):
							colorSimple(strip,Color(0,174,255),10)	
						elif(pm25value<75):
							colorSimple(strip,Color(0,255,255),10)	
					elif(value4%5==3):
						if(pm25value<8):
							colorSimple(strip,Color(pm25_user_set1_1_green,pm25_user_set1_1_red,pm25_user_set1_1_blue),10)
						elif(pm25value<15):
							colorSimple(strip,Color(pm25_user_set1_2_green,pm25_user_set1_2_red,pm25_user_set1_2_blue),10)
						elif(pm25value<20):
							colorSimple(strip,Color(pm25_user_set1_3_green,pm25_user_set1_3_red,pm25_user_set1_3_blue),10)
						elif(pm25value<25):
							colorSimple(strip,Color(pm25_user_set1_4_green,pm25_user_set1_4_red,pm25_user_set1_4_blue),10)
						elif(pm25value<37):
							colorSimple(strip,Color(pm25_user_set1_5_green,pm25_user_set1_5_red,pm25_user_set1_5_blue),10)
						elif(pm25value<50):
							colorSimple(strip,Color(pm25_user_set1_6_green,pm25_user_set1_6_red,pm25_user_set1_6_blue),10)
						elif(pm25value<75):
							colorSimple(strip,Color(pm25_user_set1_7_green,pm25_user_set1_7_red,pm25_user_set1_7_blue),10)
						else:
							colorSimple(strip,Color(pm25_user_set1_8_green,pm25_user_set1_8_red,pm25_user_set1_8_blue),10)
					elif(value4%5==4):
						if(pm25value<8):
							colorSimple(strip,Color(pm25_user_set2_1_green,pm25_user_set2_1_red,pm25_user_set2_1_blue),10)
						elif(pm25value<15):
							colorSimple(strip,Color(pm25_user_set2_2_green,pm25_user_set2_2_red,pm25_user_set2_2_blue),10)
						elif(pm25value<20):
							colorSimple(strip,Color(pm25_user_set2_3_green,pm25_user_set2_3_red,pm25_user_set2_3_blue),10)
						elif(pm25value<25):
							colorSimple(strip,Color(pm25_user_set2_4_green,pm25_user_set2_4_red,pm25_user_set2_4_blue),10)
						elif(pm25value<37):
							colorSimple(strip,Color(pm25_user_set2_5_green,pm25_user_set2_5_red,pm25_user_set2_5_blue),10)
						elif(pm25value<50):
							colorSimple(strip,Color(pm25_user_set2_6_green,pm25_user_set2_6_red,pm25_user_set2_6_blue),10)
						elif(pm25value<75):
							colorSimple(strip,Color(pm25_user_set2_7_green,pm25_user_set2_7_red,pm25_user_set2_7_blue),10)
						else:
							colorSimple(strip,Color(pm25_user_set2_8_green,pm25_user_set2_8_red,pm25_user_set2_8_blue),10)
				elif(value3%4==3):
					value5 = 0
					print"TCPmode"
					GPIO.output(17,True)
					GPIO.output(16, False)
					GPIO.output(20, False)
					GPIO.output(12, False)
					colorSimple(strip,Color(255,255,255),10)	
					if input_tcp != prev_tcp:
						if prev_tcp == False and input_tcp == True:
							value5 = value5 +1
					if(value5 == 1):
						GPIO.output(17,True)
						GPIO.output(16, True)
						GPIO.output(20, True)
						GPIO.output(12, True)
						s.listen(1)
						print ('Socket now listening')
						while True:
							print("TCP")
							print PORT
							#접속 승인
							conn, addr = s.accept()
							print("Connected by ", addr)
							#데이터 수신
							start_time = time.time() 
							data = conn.recv(1024)
							data = data.decode("utf8").strip()
							if not data: break
							print("Received: " + data)
							#수신한 데이터로 파이를 컨트롤 
							data2 = data.split(',')
							print(data2[0])
							if(data2[0] =="exit"):
								value5 =0
								value3=0
								res = "exit"
								conn.sendall(res.encode("utf-8"))
								conn.close()
								break
							elif(data2[0] =="save"):
								res = SetupLED(data)
								stationname=data2[112]
								stationname2=data2[113]								
								value5 =0
								value3=0
								conn.sendall(res.encode("utf-8"))
								conn.close()
								print(res)
								print("save data")
								break
							else: 
								stationname=data2[1]
								stationname2=data2[2]
								setup = 1
								res = ControlLED(data)
								if stationname == 'null':
									setup = 0
									res = res + ",null"
								value5 =0
								value3=0
								print res
								conn.sendall(res.encode("utf-8"))
								conn.close()
			else:
				GPIO.output(17,False)
				GPIO.output(16, False)
				GPIO.output(20, False)
				GPIO.output(12, False)
				colorSimple(strip,Color(0,0,0),10)
				value2 = 0
				value3 = 0
				value4 = 0
				value5 = 0

			prev_onoff = input_onoff
			prev_mode = input_mode
			prev_mode2 = input_mode2
			prev_tcp = input_tcp

	except KeyboardInterrupt:
		if args.clear:
			colorWipe(strip, Color(0,0,0), 10)
