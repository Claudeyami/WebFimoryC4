import React from 'react';
import { Link } from 'react-router-dom';

interface Series {
  SeriesID: number;
  Title: string;
  Description: string;
  CoverImage: string;
  CreatedAt: string;
  UpdatedAt: string;
}

interface SeriesCardProps {
  series: Series;
}

const SeriesCard: React.FC<SeriesCardProps> = ({ series }) => {
  return (
    <Link to={`/stories/${series.SeriesID}`} className="block">
      <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
        {series.CoverImage && (
          <img
            src={series.CoverImage}
            alt={series.Title}
            className="w-full h-48 object-cover"
          />
        )}
        <div className="p-4">
          <h3 className="text-lg font-semibold mb-2">{series.Title}</h3>
          <p className="text-gray-600 text-sm line-clamp-3">{series.Description}</p>
        </div>
      </div>
    </Link>
  );
};

export { SeriesCard };