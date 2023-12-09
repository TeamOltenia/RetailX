import { Input, Card, CardBody, Stat, StatLabel, Flex } from "@chakra-ui/react";

<Card>
  <CardBody>
    <Flex flexDirection="row" align="center" justify="center" w="100%">
      <Stat me="auto">
        <StatLabel fontSize="sm" color="gray.400" fontWeight="bold" pb="2px">
          Start Date
        </StatLabel>
        <Flex>
          <Input placeholder="medium size" size="md" />
        </Flex>
      </Stat>
      <IconBox as="box" h={"45px"} w={"45px"} bg="brand.200">
        <WalletIcon h={"24px"} w={"24px"} color="#fff" />
      </IconBox>
    </Flex>
  </CardBody>
</Card>;
