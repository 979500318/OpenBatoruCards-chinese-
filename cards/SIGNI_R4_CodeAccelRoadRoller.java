package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R4_CodeAccelRoadRoller extends Card {

    public SIGNI_R4_CodeAccelRoadRoller()
    {
        setImageSets("WXK01-037");

        setOriginalName("コードアクセル　ロドローラ");
        setAltNames("コードアクセルロドローラ Koodo Akuseru Rodoroora");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体が対戦相手のライフクロス１枚をクラッシュしたとき、そのシグニをバニッシュしてもよい。そうした場合、カードを２枚引く。\n" +
                "@U：あなたのターン終了時、手札を１枚捨ててもよい。そうした場合、あなたのデッキの上からシグニがめくれるまで公開する。そのシグニを場に出し、この方法で公開されたカードをトラッシュに置く。" +
                "~#：カードを１枚引く。あなたのライフクロスが４枚以下の場合、追加で【エナチャージ１】をする。"
        );

        setName("en", "Code Accel Road Roller");
        setDescription("en",
                "@U $T1: When 1 of your SIGNI crushes 1 of your opponent's life cloth, you may banish that SIGNI. If you do, draw 2 cards.\n" +
                "@U: At the end of your turn, you may discard 1 card from your hand. If you do, reveal cards from the top of your deck until you reveal a SIGNI. Put that SIGNI onto the field, and put the rest into the trash." +
                "~#Draw 1 card. If you have 4 or less life cloth, additionally [[Ener Charge 1]]."
        );

		setName("zh_simplified", "加速代号 压路机");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵1只把对战对手的生命护甲1张击溃时，可以把那只精灵破坏。这样做的场合，抽2张牌。\n" +
                "@U :你的回合结束时，可以把手牌1张舍弃。这样做的场合，从你的牌组上面直到把精灵公开为止。那张精灵出场，这个方法公开的牌放置到废弃区。" +
                "~#抽1张牌。你的生命护甲在4张以下的场合，追加[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && isOwnCard(getEvent().getSource()) && CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getEvent().getSourceCardIndex().isSIGNIOnField() && playerChoiceActivate() && banish(getEvent().getSourceCardIndex()))
            {
                draw(2);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndexSIGNI = revealUntil(getOwner(), cardIndex -> CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()));
                putOnField(cardIndexSIGNI);
                
                trash(getCardsInRevealed(getOwner()));
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            
            if(getLifeClothCount(getOwner()) <= 4)
            {
                enerCharge(1);
            }
        }
    }
}
