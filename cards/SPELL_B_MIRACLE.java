package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_MIRACLE extends Card {

    public SPELL_B_MIRACLE()
    {
        setImageSets("WXK01-041");

        setOriginalName("MIRACLE");
        setAltNames("ミラクル Mirakuru");
        setDescription("jp",
                "以下の３つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2対戦相手は手札を１枚捨てる。\n" +
                "$$3対戦相手のすべてのシグニを凍結する。\n\n" +
                "@U：対戦相手のターン終了時、あなたの手札が０枚の場合、%Bを支払ってもよい。そうした場合、このカードをトラッシュから手札に加える。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "MIRACLE");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Your opponent discards 1 card from their hand.\n" +
                "$$3 Freeze all of your opponent's SIGNI.\n\n" +
                "@U: At the end of your opponent's turn, if there are 0 cards in your hand, you may pay %B. If you do, add this card from your trash to your hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "MIRACLE");
        setDescription("zh_simplified", 
                "从以下的3种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 对战对手把手牌1张舍弃。\n" +
                "$$3 对战对手的全部的精灵冻结。\n" +
                "@U :对战对手的回合结束时，你的手牌在0张的场合，可以支付%B。这样做的场合，这张牌从废弃区加入手牌。" +
                "~#以下选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setActiveLocation(CardLocation.TRASH);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            switch(spell.getChosenModes())
            {
                case 1 -> draw(1);
                case 1<<1 -> discard(getOpponent(), 1);
                case 1<<2 -> freeze(getSIGNIOnField(getOpponent()));
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH &&
               getHandCount(getOwner()) == 0 && payEner(Cost.color(CardColor.BLUE, 1)))
            {
                addToHand(getCardIndex());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
    }
}
