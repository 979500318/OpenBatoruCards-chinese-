package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_CinerariaNaturalPlant extends Card {

    public SIGNI_G2_CinerariaNaturalPlant()
    {
        setImageSets("WX24-P4-080");

        setOriginalName("羅植　サイネリア");
        setAltNames("ラショクサイネリア Rashoku Saineria");
        setDescription("jp",
                "@E：あなたのエナゾーンに＜植物＞のシグニが３枚以上ある場合、あなたのエナゾーンから＜植物＞のシグニを１枚まで対象とし、それを場に出す。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Cineraria, Natural Plant");
        setDescription("en",
                "@E: If there are 3 or more <<Plant>> SIGNI in your ener zone, target up to 1 <<Plant>> SIGNI from your ener zone, and put it onto the field.\n" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "罗植 瓜叶菊");
        setDescription("zh_simplified", 
                "@E :你的能量区的<<植物>>精灵在3张以上的场合，从你的能量区把<<植物>>精灵1张最多作为对象，将其出场。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLANT).fromEner();
            if(filter.getValidTargetsCount() >= 3)
            {
                CardIndex target = playerTargetCard(0,1, filter.playable()).get();
                putOnField(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
