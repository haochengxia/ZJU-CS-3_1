#include <iostream>
#include <opencv2/opencv.hpp>
#include "opencv2/imgproc.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/ml.hpp"
#include "opencv2/objdetect.hpp"
#include "./transition.h"
#include <ctime>

#define random(x) rand()%(x)

int main(int argc, char* argv[])
{

	// initialize
	cv::VideoWriter writer;
	bool isColor = true;
	cv::Size finalSize = cv::Size(FRAME_WIDTH, FRAME_HEIGHT);
	cv::String videoName = "out.avi";	
	std::vector<cv::String> filenames;
	std::vector<cv::String> imagenames;
	cv::String videoname = "default";
	

	// solve the argument 
	if (argc != 2) {
		std::cout << "Usage: MakeMyVideo.exe [your file directory]" << std::endl;
		return 0;
	}

	// read the directory
	cv::String folder = argv[1];
	cv::glob(folder, filenames);
	std::cout << "Loading..." << std::endl;
	for (size_t i = 0; i < filenames.size(); ++i)
	{
		std::cout << filenames[i] << std::endl;
		cv::Mat src = cv::imread(filenames[i]);
		if (!src.data) {
			cv::VideoCapture capture = cv::VideoCapture(filenames[i]);
			// get fps of video
			if (capture.isOpened()) {
				writer = cv::VideoWriter(videoName, CV_FOURCC('M', 'J', 'P', 'G'), capture.get(CV_CAP_PROP_FPS), finalSize, isColor);
				videoname = filenames[i];
				std::cout << "Video found! Video path: <" << videoname << ">" << std::endl;
				std::cout << "Video fps is:" << capture.get(CV_CAP_PROP_FPS) << std::endl;
			}
			else std::cerr << "Warning: Problem loading image/video !!! Please keep the file format correct!!! " << std::endl;
		}
		else {
			cv::Mat imgResized;
			imagenames.push_back(filenames[i]);
			std::cout << "Image found! Image[" << imagenames.size() << "] path: <" << filenames[i] << ">" << std::endl;
		}
	}

	// solve the picture
	for (int i = 0; i < imagenames.size(); ++i) {
		int next = 0;
		// get the current image
		cv::Mat src = cv::imread(imagenames[i]);
		cv::Mat imgResized;
		resize(src, imgResized, finalSize);
		// try to get next image
		cv::Mat nextImg;
		if (i < imagenames.size() - 1) {
			// it means there is a next picture we can use
			src = cv::imread(imagenames[i + 1]);
			resize(src, nextImg, finalSize);
			next = 1;
		}
		cv::putText(imgResized, "3170102492 XiaHaocheng", cvPoint(30, 320), cv::FONT_HERSHEY_TRIPLEX, 1.0, cvScalar(200, 200, 250), 1, CV_AA);
		for (int j = 0; j < IMAGE_TIME * 30; j++) {
			writer.write(imgResized);
		}
		// inorder to add the transiction effect, we need to get the next picture if it exsits
		if (next == 1) {
			cv::putText(nextImg, "3170102492 XiaHaocheng", cvPoint(30, 320), cv::FONT_HERSHEY_TRIPLEX, 1.0, cvScalar(200, 200, 250), 1, CV_AA);
			// we random use these two kinds of transaction
			switch(random(2)) {
				case 0: GradualChangeTransition(writer, imgResized, nextImg); break;
				case 1: RollInTransition(writer, imgResized, nextImg); break;
				default: {}
			}
		}
	}

	// solve the video
	if (videoname != "default") {
		cv::VideoCapture capture = cv::VideoCapture(videoname);
		cv::Mat frame, frameResized;
		if (!capture.isOpened())
		{
			std::cout << "Video load failed!" << std::endl;
			return -1;
		}
		else {
			std::cout << "Video load Success!" << std::endl;
			while (true) {
				capture >> frame;
				if (frame.empty())
					break;
				resize(frame, frameResized, finalSize);
				cv::putText(frameResized, "3170102492 XiaHaocheng", cvPoint(30, 320), cv::FONT_HERSHEY_TRIPLEX, 1.0, cvScalar(200, 200, 250), 1, CV_AA);
				writer.write(frameResized);
			}
			writer.release();
		}
	}
	std::cout << "Done!!!" << std::endl;
	return 0;
}

