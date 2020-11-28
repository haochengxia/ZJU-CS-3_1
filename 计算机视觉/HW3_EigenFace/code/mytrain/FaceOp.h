#pragma once

#include <opencv2/opencv.hpp>
#include <iostream>
#include <string>
#include <vector>

#define MYEXT ".png"

const int WIDTH = 73;
const int HEIGHT = 89;




class Face {
public:
	cv::Mat originFaceImg;
	cv::Mat grayFaceImg;
	cv::Mat transformedFaceImg;
	int x1, y1, x2, y2;
	cv::Mat trans_mat;
	cv::Mat_<double> equalized_mat;
	cv::Mat_<double> vect;
	void load(std::string& path, std::string ext);
	void load(std::string& path);

	void load_eye_pos(std::string& path);

	void transform();
};

class FaceModel {
public:
	int num_of_faces = 210;
	int num_of_persons = 42;
	int faces_per_person = 5;
	std::vector<Face*> faces;
	std::vector<cv::Mat_<double>> _samples;
	cv::Mat_<double> samples;

	void load(std::string& path);
};