import { useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import Link from 'next/link'
import * as V from 'victory'
import { VictoryChart, VictoryLine, VictoryAxis} from 'victory'

export default function ItemPage() {
  //grab sku from url: (.../sku/)
  const router = useRouter()
  const {sku} = router.query

  //bounds for graphing
  const [upperPriceBound, setUpperPriceBound] = useState(150)
  const [lowerPriceBound, setLowerPriceBound] = useState(0)

  //graph data variables
  const [data, setData] = useState([])
  const [priceData, setPriceData] = useState([])
  const [formattedData, setFormattedData] = useState([])

  //for page loading
  const [isLoading, setLoading] = useState(false)

  
  //fetch item details on page load, requires router 
  //in dependency array
  useEffect(() => {
    //set page loading (should be pretty fast but just in case)
    setLoading(true)
    //fetch product data
    console.log(sku)
    fetch(`http://hfdb.duckdns.org:8080/grabProductDetails/${sku}`)
      .then((res) => res.json())
      .then((data) => {
        //fill data array
        setData(data)
        //set graph bounds
        setUpperPriceBound(new Number(2*data.price / 100))
        setLowerPriceBound(new Number(0))

      }) //fetch all price information 
    fetch(`http://hfdb.duckdns.org:8080/grabRetailPriceHistory/${sku}/1900-01-01T01%3A01%3A01Z/2100-01-01T01%3A01%3A01Z`)
    .then((res) => res.json())
      .then((priceData) => {
        setFormattedData(priceData.map((element)=>{
          let temp = new String(element.ts)
          let temp2 = new Number(element.price/100)
          
          //set "x" values as the dates return from the API call, and y as the price
          return {x: new Date(temp.substring(0,4), new Number(temp.substring(5,7)-1), temp.substring(8,10) ), 
                  y: temp2}
        }))
        setTimeout(()=>{
          //timeout so the data can be fetched and processed in time
          setPriceData(priceData)
        console.log(priceData)
        console.log("formattedData: ")
        console.log(formattedData)
        setLoading(false)
        },1900)
        
      })
      .catch((e)=>{
        console.log("Waiting for data from API:")
      })

  }, [router])

  if (isLoading) return <h1>Loading...</h1>
  //additional timeout
  setTimeout(()=>{

  },3000)

  //page layout, including item information and graph
  return (
    <div>
      <div>
       
        
        {/*When making Links, you must include https:// or it will route to the current url/{href} */}
      </div>
      
      <div className='ItemDescription'>
      <center>
        <img src={data.imgURL}></img>
        
        <p>{"$"+data.price/100}</p>
      </center>
      <Link href = {`https://www.harborfreight.com/${data.canonicalURL}`}>View on Harbor Freight's Website</Link>
        
        <h1>{data.name}</h1>
      <div className="chart" style={{ width: "800", height: "1000"}}>
         <VictoryChart
         title={data.name}
         
         scale={{x: "time" }}
        height = "800"
        width="1000"
        >
          <VictoryAxis label="Time" />
          <VictoryAxis dependentAxis label="Price (USD)" />
          <VictoryLine
         domain={{x: [new Date(2022, 8, 17), new Date()],y: [lowerPriceBound, upperPriceBound]}}
         labels={({ datum }) => ("$"+datum.y)}
          data={formattedData}
            interpolation="step"
            style={{
              data: { stroke: "#c43a31" },
              parent: { border: "1px solid #ccc"}
            }}
            
          />
          
        </VictoryChart>
        </div>
      </div>
        
    </div>
    
  )
}