package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_G2_MorayWaterPhantom extends Card {

    public SIGNI_G2_MorayWaterPhantom()
    {
        setImageSets("WXDi-P09-074");

        setOriginalName("幻水　ウツボ");
        setAltNames("ゲンスイウツボ Gensui Utsubo");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。あなたの手札が５枚以上ある場合、代わりにターン終了時まで、それのパワーを＋5000する。\n" +
                "@E #C #C：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニよってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Moray, Phantom Aquatic Beast");
        setDescription("en",
                "@E: Target SIGNI on your field gets +3000 power until end of turn. If you have five or more cards in your hand, it gets +5000 power until end of turn instead.\n" +
                "@E #C #C: Add target SIGNI from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Moray, Water Phantom");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and until end of turn, it gets +3000 power. If there are 5 or more cards in your hand, until end of turn, it gets +5000 power instead.\n" +
                "@E #C #C: Target 1 SIGNI from your ener zone, and add it to your hand." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "幻水 海鳝");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。你的手牌在5张以上的场合，作为替代，直到回合结束时为止，其的力量+5000。\n" +
                "@E #C #C:从你的能量区把精灵1张作为对象，将其加入手牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new CoinCost(2), this::onEnterEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, getHandCount(getOwner()) < 5 ? 3000 : 5000, ChronoDuration.turnEnd());
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
