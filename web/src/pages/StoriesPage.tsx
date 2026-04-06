import React, { useEffect, useState } from 'react';
import { SeriesCard } from '../components/content/SeriesCard';
import { api } from '../lib/api';

interface Series {
  SeriesID: number;
  Title: string;
  Description: string;
  CoverImage: string;
  CreatedAt: string;
  UpdatedAt: string;
}

const StoriesPage: React.FC = () => {
  const [series, setSeries] = useState<Series[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSeries = async () => {
      try {
        const response = await api.get('/series');
        setSeries(response.data);
      } catch (error) {
        console.error('Failed to fetch series:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchSeries();
  }, []);

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Stories</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {series.map((item) => (
          <SeriesCard key={item.SeriesID} series={item} />
        ))}
      </div>
    </div>
  );
};

export default StoriesPage;