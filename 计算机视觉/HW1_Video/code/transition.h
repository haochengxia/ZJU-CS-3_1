#pragma once
#ifndef TRANSITION_H
#define TRASITION_H
#define TRASITION_TIME 1	// TRASITION_TIME 1 means the transaction time is 30*1 frame, you can define it as you like
#define FRAME_FPS  30		// VIDEO FPS is 30
#define FRAME_WIDTH  640	// VIDEO WIDTH is 640
#define FRAME_HEIGHT  360	// VIDEO HEIGHT is 360
#define IMAGE_TIME 2		// IMAGE_TIME 2 means a image will be displayed 30*2 frame in the video, you can define it as you like
#include <opencv2/opencv.hpp>
#include "opencv2/imgproc.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/ml.hpp"
#include "opencv2/objdetect.hpp"
#include <opencv2/core/core.hpp>    
#include <opencv2/imgproc/imgproc.hpp>

void GradualChangeTransition(cv::VideoWriter writer, cv::Mat src1, cv::Mat src2); // Gradual Change Transition
void RollInTransition(cv::VideoWriter writer, cv::Mat src1, cv::Mat src2); //Roll In Transition

#endif // !TRANSITION_H

