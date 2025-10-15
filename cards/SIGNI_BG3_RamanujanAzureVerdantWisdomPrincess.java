package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_BG3_RamanujanAzureVerdantWisdomPrincess extends Card {

    public SIGNI_BG3_RamanujanAzureVerdantWisdomPrincess()
    {
        setImageSets("WX24-P4-053");

        setOriginalName("蒼翠英姫　ラマヌジャン");
        setAltNames("ソウスイエイキラマヌジャン Sousuieiki Ramanujan");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の３つから１つを選ぶ。\n" +
                "$$1あなたの手札の枚数があなたのエナゾーンにあるカードの枚数より少ない場合、カードを１枚引く。\n" +
                "$$2あなたのエナゾーンにあるカードの枚数があなたの手札の枚数より少ない場合、【エナチャージ１】をする。\n" +
                "$$3あなたの手札の枚数とあなたのエナゾーンにあるカードの枚数が同じ場合、カードを１枚引き【エナチャージ１】をする。"
        );

        setName("en", "Ramanujan, Azure Verdant Wisdom Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If the number of cards in your hand is less than the number of cards in your ener zone, draw 1 card.\n" +
                "$$2 If the number of cards in your ener zone is less than the number of cards in your hand, [[Ener Charge 1]].\n" +
                "$$3 If the number of cards in your hand and the number of cards in your ener zone are equal, draw 1 card and [[Ener Charge 1]]."
        );

		setName("zh_simplified", "苍翠英姬 拉马努金");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的3种选1种。\n" +
                "$$1 你的手牌的张数比你的能量区的牌的张数少的场合，抽1张牌。\n" +
                "$$2 你的能量区的牌的张数比你的手牌的张数少的场合，[[能量填充1]]。\n" +
                "$$3 你的手牌的张数和你的能量区的牌的张数相同的场合，抽1张牌并[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int modes = playerChoiceMode();
            int countHand = getHandCount(getOwner());
            int countEner = getEnerCount(getOwner());
            switch(modes)
            {
                case 1 -> {
                    if(countHand < countEner)
                    {
                        draw(1);
                    }
                }
                case 1<<1 -> {
                    if(countEner < countHand)
                    {
                        enerCharge(1);
                    }
                }
                case 1<<2 -> {
                    if(countEner == countHand)
                    {
                        draw(1);
                        enerCharge(1);
                    }
                }
            }
        }
    }
}
