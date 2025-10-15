package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_BluePumbagoNaturalPlant extends Card {

    public SIGNI_B3_BluePumbagoNaturalPlant()
    {
        setImageSets("WXDi-P11-069");

        setOriginalName("羅植　ルリマツリ");
        setAltNames("ラショクルリマツリ Rashoku Rurimutsuri");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：対戦相手のターン終了時、カードを１枚引く。\n\n" +
                "@U：あなたがこのカードを捨てたとき、手札を１枚捨ててもよい。そうした場合、【エナチャージ１】をする。" +
                "~#：カードを３枚引く。"
        );

        setName("en", "Plumbago, Natural Plant");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, this SIGNI gets +3000 power.\n" +
                "@U: At the end of your opponent's turn, draw a card.\n\n" +
                "@U: When you discard this card, you may discard a card. If you do, [[Ener Charge 1]]." +
                "~#Draw three cards."
        );
        
        setName("en_fan", "Blue Plumbago, Natural Plant");
        setDescription("en_fan",
                "@C: As long as there are 4 or more cards in your hand, this SIGNI gets +3000 power.\n" +
                "@U: At the end of your opponent's turn, draw 1 card.\n\n" +
                "@U: When you discard this card, you may discard 1 card from your hand. If you do, [[Ener Charge 1]]." +
                "~#Draw 3 cards."
        );

		setName("zh_simplified", "罗植 蓝雪花");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+3000。\n" +
                "@U :对战对手的回合结束时，抽1张牌。\n" +
                "@U :当你把这张牌舍弃时，可以把手牌1张舍弃。这样做的场合，[[能量填充1]]。" +
                "~#抽3张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEff1Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }

        private void onAutoEff2()
        {
            if(discard(0,1).get() != null)
            {
                enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            draw(3);
        }
    }
}

