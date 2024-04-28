import React, { useState, useEffect } from 'react';
import { useRouter } from "next/router";
import Link from 'next/link';
import axios from 'axios';
import '../src/app/css/globals.css'
import Header from '@/app/Header';
import PagesLayout from "../src/app/layout";
import Image from 'next/image'
import loading from "../public/loading.svg"

const CharacterInfo = () => {
  const [characterData, setCharacterData] = useState(null);
  const router = useRouter();

  useEffect(() => {
    const { name } = router.query;
    if (name) {
      axios.get(`${process.env.NEXT_PUBLIC_API_URL}/characters/name?name=${name}`)
        .then(response => {
          const character = response.data[0];
          setCharacterData(character);
        })
        .catch(error => {
          console.error('Ошибка при получении данных:', error);
        });
    }
  }, [router.query.name]);

  return (
    <div>
      {characterData ? (<CharacterInfoContent characterData={characterData} />) : (<LoadingStatus />)}
    </div>
  );
};

export default CharacterInfo;

const CharacterInfoContent = ({ characterData }) => {
  if (!characterData) {
    return null;
  }

  return (
    <PagesLayout>
      <Header />
      <div className="absolute left-0 w-full h-7 object-cover object-center z-[-2] -top-40">
        {/* <Image className="blur opacity-40" src={bg} /> */}
      </div>
      <div className="m-10 relative z-0">
        <div className="flex flex-row">
          <div className="z-[-1]">
            <div className="absolute top-0 left-40 w-48 h-48 rounded-full bg-gradient-to-r from-blue-400 via-yellow-500 to-green-500 blur-3xl"></div>
            <div className="absolute top-80 w-64 h-64 rounded-full bg-gradient-to-r from-blue-400 to-green-500 scale blur-3xl"></div>
          </div>
          <img
            src={characterData.picUrl}
            className="h-auto w-80 scale-105 rounded-3xl hover:-rotate-3 hover:scale-95 transition ease-out"
            alt={characterData.name}
          />
          <div className="flex flex-col pl-20">
            <h1 className="text-5xl font-extrabold">{characterData.name}</h1>
            <div className="mt-12">
              <div className="font-light pt-16">
                <h2 className="font-bold  text-xl">Information:</h2>
                <p>Name: {characterData.name}</p>
                <p>Power: {characterData.power}</p>
                <p>Agility: {characterData.agility}</p>
                <p>Intelligence: {characterData.intelligence}</p>
                <p>AttackType: {characterData.attackType}</p>
                <p>Abilities:</p>
                <div className="flex space-x-2">
                  {characterData.abilities.map((ability, index) => (
                    <React.Fragment key={ability.name}>
                      <Link
                        className="hover:underline"
                        href={{
                          pathname: '/abilities',
                          query: { abilities: ability.name },
                        }}
                      >
                        <img src={ability.picUrl} alt={ability.name} />
                      </Link>
                    </React.Fragment>
                  ))}
              </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </PagesLayout>
  );
};


const LoadingStatus = () => {
  return (
    <div className="flex relative min-h-screen place-content-center items-center">
      <Image className="animate-spin mr-3" src={loading} alt='loading' width={48} height={48} />
      <p className="font-bold text-4xl">Загрузка...</p>
    </div>
  )
}
