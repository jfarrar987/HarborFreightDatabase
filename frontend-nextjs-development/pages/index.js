import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
// Home Page for Harbor Freight Database
export default function Home() {
  return (
    <div className="homePage">
    <center><h2>Welcome to the Harbor Freight Database!</h2>
    <p>This database details the price changes on Harbor Freight's products. <br></br>
    <br></br>
      <big>Ready to start looking for the hottest deals from Harbor Freight? <a href="/search">Search now</a></big><br></br>
      <br></br>
      <br></br>
      For more information about this website, visit our <a href="/about">About Page</a>. <br></br>
      Looking for the original Harbor Freight website? <a href="https://www.harborfreight.com/">Click here</a></p>
      
    </center></div>
  )
}
