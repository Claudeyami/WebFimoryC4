import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { api } from '../lib/api';

interface Series {
  SeriesID: number;
  Title: string;
  Description: string;
  CoverImage: string;
  CreatedAt: string;
  UpdatedAt: string;
}

interface Chapter {
  ChapterID: number;
  Title: string;
  Content: string;
  ChapterNumber: number;
  CreatedAt: string;
  UpdatedAt: string;
}

const StoryPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [series, setSeries] = useState<Series | null>(null);
  const [chapters, setChapters] = useState<Chapter[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSeriesAndChapters = async () => {
      if (!id) return;
      try {
        const [seriesResponse, chaptersResponse] = await Promise.all([
          api.get(`/series/${id}`),
          api.get(`/series/${id}/chapters`)
        ]);
        setSeries(seriesResponse.data);
        setChapters(chaptersResponse.data);
      } catch (error) {
        console.error('Failed to fetch series or chapters:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchSeriesAndChapters();
  }, [id]);

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  if (!series) {
    return <div className="text-center">Series not found</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold mb-4">{series.Title}</h1>
        <p className="text-gray-600 mb-4">{series.Description}</p>
        {series.CoverImage && (
          <img src={series.CoverImage} alt={series.Title} className="w-full max-w-md h-auto rounded-lg" />
        )}
      </div>
      <div>
        <h2 className="text-2xl font-bold mb-4">Chapters</h2>
        <div className="space-y-2">
          {chapters.map((chapter) => (
            <div key={chapter.ChapterID} className="border rounded-lg p-4 hover:bg-gray-50">
              <h3 className="text-lg font-semibold">
                Chapter {chapter.ChapterNumber}: {chapter.Title}
              </h3>
              <p className="text-sm text-gray-500">{new Date(chapter.CreatedAt).toLocaleDateString()}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default StoryPage;