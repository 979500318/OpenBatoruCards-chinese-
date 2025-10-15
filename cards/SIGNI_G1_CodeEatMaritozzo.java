package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G1_CodeEatMaritozzo extends Card {

    public SIGNI_G1_CodeEatMaritozzo()
    {
        setImageSets("WXDi-P11-073");

        setOriginalName("コードイート　マリトッツォ");
        setAltNames("コードイートマリトッツォ Koodo Iito Maritottso");
        setDescription("jp",
                "@U $T2：あなたのエナゾーンにカード１枚が置かれたとき、ターン終了時まで、このシグニのパワーを＋2000する。\n" +
                "@E %X：あなたのデッキの上からカードを３枚見る。その中からカード１枚をエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Maritozzo, Code: Eat");
        setDescription("en",
                "@U $T2: Whenever a card is put into your Ener Zone, this SIGNI gets +2000 power until end of turn.\n" +
                "@E %X: Look at the top three cards of your deck. Put a card from among them into your Ener Zone. Put the rest on the bottom of your deck in any order." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Code Eat Maritozzo");
        setDescription("en_fan",
                "@U $T2: Whenever a card is put into your ener zone, until end of turn, this SIGNI gets +2000 power.\n" +
                "@E %X: Look at the top 3 cards of your deck. Put 1 card from among them into your ener zone, and put the rest on the bottom of your deck in any order." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "食用代号 罗马生乳包");
        setDescription("zh_simplified", 
                "@U $T2 :当你的能量区有1张牌放置时，直到回合结束时为止，这只精灵的力量+2000。\n" +
                "@E %X:从你的牌组上面看3张牌。从中把1张牌放置到能量区，剩下的任意顺序放置到牌组最下面。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 2000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ENER).own().fromLooked()).get();
            putInEner(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}

