import { useState, useEffect, useRef } from "react";
import CourseCard from "../../components/course/CourseCard";
import { getTrendingCourses, getAllCourses } from "../../service/courseService";
import InstructorSection from "../../components/instructor/InstructorSection";
import AdvertisementSection from "../Advertisement/AdvertisementSection";
import "./home.css";

function Home() {

  const categories = [
    "All",
    "Artificial Intelligence",
    "Python",
    "Java",
    "Spring Boot",
    "React"
  ];

  const [selectedCategory, setSelectedCategory] = useState("All");
  const [trendingCourses, setTrendingCourses] = useState([]);
  const [courses, setCourses] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [showLeftArrow, setShowLeftArrow] = useState(false);

  // Trending slider reference
  const trendingRef = useRef(null);
  useEffect(() => {
    loadTrendingCourses();
    loadCourses();
  }, []);

  // Detect slider position
  useEffect(() => {
    const slider = trendingRef.current;
    const handleScroll = () => {
      if (slider.scrollLeft > 0) {
        setShowLeftArrow(true);
      }
      else {
        setShowLeftArrow(false);
      }
    };
    if (slider) {
      slider.addEventListener("scroll", handleScroll);
    }
    return () => {

      if (slider) {
        slider.removeEventListener("scroll", handleScroll);
      }
    };
  }, [trendingCourses]);

  const loadTrendingCourses = async () => {
    const response = await getTrendingCourses();
    setTrendingCourses(response.data);
  };
  const loadCourses = async () => {
    const response = await getAllCourses();
    setCourses(response.data);
  };

  // Arrow scroll
  const scrollTrending = (direction) => {
    trendingRef.current.scrollBy({
      left: direction === "left" ? -320 : 320,
      behavior: "smooth"
    });
  };
  const filteredCourses =
    courses.filter(course =>
      (selectedCategory === "All" || course.category === selectedCategory) &&
      course.title.toLowerCase().includes(searchText.toLowerCase())
    );

  return (
    <div>
      {/* Advertisment Section */}
      <AdvertisementSection />

      {/* Hero Section */}
      <h3 className="fw-bold">
        Skills to transform your career and life
      </h3>
      <p className="text-muted">
        From critical skills to technical topics,
        our LearnCourse supports your professional development.
      </p>

      {/* Divider */}
      <hr className="section-divider" />

      {/* Trending Courses */}
      <h4 className="mt-4 mb-3 fw-bold">
        Top Rated Courses 🔥
      </h4>
      <div className="position-relative">
        {/* Left Arrow */}
        {
          showLeftArrow && (
            <button
              className="btn btn-dark position-absolute top-50 translate-middle-y"
              style={{
                zIndex: 2,
                left: "-20px"
              }}
              onClick={() => scrollTrending("left")}
            >
              ❮
            </button>
          )
        }
        {/* Course Slider */}
        <div
          ref={trendingRef}
          className="row g-3 flex-nowrap overflow-hidden"
        >
          {
            trendingCourses
              .filter(course => course.averageRating >= 3)
              .map(course => (
                <div
                  className="col-lg-3 col-md-4 col-sm-6"
                  key={course.id}
                >
                  <CourseCard course={course} />
                </div>
              ))
          }
        </div>
        {/* Right Arrow */}
        <button
          className="btn btn-dark position-absolute top-50 translate-middle-y"
          style={{
            zIndex: 2,
            right: "-20px"
          }}
          onClick={() => scrollTrending("right")}
        >
          ❯
        </button>
      </div>

      {/* Instructors */}
      <InstructorSection />

      {/* All Courses */}
      <h4 className="mt-5 mb-3 fw-bold">
        Explore All Courses
      </h4>

      {/* Category Filter + Search */}
      <div className="course-toolbar mb-4">
        <div className="course-filter">
          {
            categories.map(cat => (
              <button
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={
                  selectedCategory === cat
                    ? "filter-btn active"
                    : "filter-btn"
                }
              >
                {cat}
              </button>
            ))
          }
        </div>

        <div className="course-search">
          <input
            type="text"
            placeholder="Search courses..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
          />
        </div>
      </div>
      {/* All Course List */}
      <div className="row g-3">
        {
          filteredCourses.map(course => (
            <div
              className="col-lg-3 col-md-4 col-sm-6"
              key={course.id}
            >
              <CourseCard course={course} />
            </div>
          ))
        }
      </div>
    </div>
  );
}
export default Home;