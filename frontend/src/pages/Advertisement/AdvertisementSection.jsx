import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAdvertisements } from "../../service/advertisementService";
import "../Advertisement/AdvertisementSection.css";

function AdvertisementSection() {

  const [advertisements, setAdvertisements] = useState([]);
  const [currentAd, setCurrentAd] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    loadAdvertisements();
  }, []);

  useEffect(() => {
    if (advertisements.length > 1) {
      const interval = setInterval(() => {
        setCurrentAd(prev =>
          prev === advertisements.length - 1 ? 0 : prev + 1
        );
      }, 5000);

      return () => clearInterval(interval);
    }
  }, [advertisements]);

  const loadAdvertisements = async () => {
    const response = await getAdvertisements();
    setAdvertisements(response.data);
  };

  const handleClick = () => {
    navigate("/courses");
  };

  if (advertisements.length === 0) {
    return null;
  }

  const ad = advertisements[currentAd];

  return (
    <div className="mb-5">
      <div className="advertisement-card">

        <img
          src={`http://localhost:8080/${encodeURI(ad.imageUrl)}`}
          alt={ad.title}
        />

        <div className="advertisement-content">

          <button
            className="advertisement-btn"
            onClick={handleClick}
          >
            Explore Now
          </button>

        </div>

      </div>
    </div>
  );
}

export default AdvertisementSection;