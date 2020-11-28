#include "FaceOp.h"

void FaceModel::load(std::string& dirPath) {
for (int i = 1; i <= num_of_persons; i++)
{
	for (int j = 1; j <= faces_per_person; ++j) {
		// get the PATH -> dirPath/xx/xx.png
		std::string entry_path = dirPath + "/" + std::to_string(i) + "/" + (j < 10 ? '0' + std::to_string(j) : std::to_string(j));
		Face* face = new Face();
		face->load(entry_path, MYEXT);
		faces.push_back(face);
		_samples.push_back(face->vect);
	}
}
	hconcat(_samples, samples);
	}


void Face::load(std::string& path, std::string ext) {
	load_eye_pos(path);
	originFaceImg = cv::imread(path + ext);
	//imshow("originFaceImg", originFaceImg);
	grayFaceImg = imread(path + ext, cv::IMREAD_GRAYSCALE);
	//imshow("grayFaceImg", grayFaceImg);
	transform();
}
void Face::load(std::string& path) {
	std::string nameWithoutPost = path.substr(0, path.length() - 4);
	load_eye_pos(nameWithoutPost);
	originFaceImg = cv::imread(path);
	grayFaceImg = cv::imread(path, cv::IMREAD_GRAYSCALE);
	transform();
}

void Face::load_eye_pos(std::string& path) {
	std::ifstream file(path + ".txt", std::ifstream::in);
	file >> x1 >> y1 >> x2 >> y2;
}

void Face::transform() {
	cv::Point center((x1 + x2) / 2, (y1 + y2) / 2);
	double angle = atan((double)(y2 - y1) / (double)(x2 - x1)) * 180.0 / CV_PI;
	trans_mat = getRotationMatrix2D(center, angle, 1.0);
	trans_mat.at<double>(0, 2) += 37.0 - center.x;
	trans_mat.at<double>(1, 2) += 30.0 - center.y;
	warpAffine(grayFaceImg, transformedFaceImg, trans_mat, grayFaceImg.size() * 4 / 5);
	cv::equalizeHist(transformedFaceImg, transformedFaceImg);
	transformedFaceImg.copyTo(equalized_mat);
	//std::cout<<"eq"<<equalized_mat.cols << std::endl;
	vect = equalized_mat.reshape(1, 1).t();
}