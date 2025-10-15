package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_VanadiniteNaturalStone extends Card {

    public SIGNI_R1_VanadiniteNaturalStone()
    {
        setImageSets("WX24-P1-059");

        setOriginalName("羅闘石　アメジスト");
        setAltNames("ラトウセキアメジスト Ratouseki Ameshisuto");
        setDescription("jp",
                "@U $TO $T1：あなたが手札を１枚捨てたとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Vanadinite, Natural Stone");
        setDescription("en",
                "@U $TO $T1: When you discard 1 card from your hand, until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "罗石 褐铅石");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当你把手牌1张舍弃时，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
