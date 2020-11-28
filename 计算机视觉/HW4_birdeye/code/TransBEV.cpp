
/*

Homework#4:
•《Learning OPENCV》
•Camera calibration
•Ex 11-1, 11-2
•Bird’s-eye view transformation
•Ex 12-1
•Combine them together

Camera calibration & Bird’s-eye view transformation 
*/

#include "TransBEV.h"
#include <iostream>

int main(int argc, char** argv)
{	
	// according to the number of para to judge the picture from Camera or Disk
	int disk = -1; // default -1 means no format match
	if (argc == 8) disk = 0; // 0 means from Camera
	else if (argc == 9) disk = 1; // 1 means from disk
	switch (disk)
	{
	case 0:
		ex18_1(argc, argv);
		break;
	case 1:
		ex18_1_disk(argc, argv);
		break;
	default:
		std::cout << "please enter the correct format parameters\n"
			<< "eg: TransBEV.exe 12 12 28 100 0.5 D:/calibration/ intrinsics.xml D:/birdseye/IMG_0215L.jpg \n"
			<< "from disk: TransBEV.exe <1board_width> <2board_height> <3number_of_boards> \n"
			<< "<4ms_delay_framee_capture> <5image_scaling_factor> <6path_to_calibration_images> \n"
			<< "<7path/camera_calib_filename> <8path/chessboard_image> \n" 
			<< "from camera: TransBEV.exe <1board_width> <2board_height> <3number_of_boards> \n"
			<< "<4if_video,_delay_between_framee_capture> <5image_scaling_factor> \n"
			<< "<6path/camera_calib_filename> <7path/chessboard_image>"
			<< std::endl;
		return -1;
	}
	ex19_1(argc, argv);
	return 0;
}
