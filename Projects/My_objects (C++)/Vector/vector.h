#ifndef VECTOR_H_
#define VECTOR_H_

#include <iostream>

namespace VECTOR {
class Vector {
   public:
    enum Mode { RECT, POL };

   private:
    double x;
    double y;
    double mag;
    double ang;
    Mode mode;

    void set_mag();
    void set_ang();
    void set_x();
    void set_y();

   public:
    Vector(double num_1 = 0, double num_2 = 0, Mode type = RECT);
    void reset(double num_1 = 0, double num_2 = 0, Mode type = RECT);
    // get private values
    double x_val() { return x; }
    double y_val() { return y; }
    double mag_val() { return mag; }
    double ang_val() { return ang; }
    // set mode
    void polar_mode();
    void rect_mode();
    // operator overload
    Vector operator+(Vector& v) const;
    Vector operator-(Vector& v) const;
    Vector operator-() const;
    Vector operator*(double number) const;
    // friends
    friend Vector operator*(double number, const Vector& v);
    friend std::ostream& operator<<(std::ostream& os, const Vector& v);
};
}  // namespace VECTOR

#endif