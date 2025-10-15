package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;

public final class SIGNI_G2_PlaygroundSlideSecondPlay extends Card {

    public SIGNI_G2_PlaygroundSlideSecondPlay()
    {
        setImageSets("WX25-P1-094");

        setOriginalName("弍ノ遊　スベリダイ");
        setAltNames("ニノユウスベリダイ Ni no Yuu Suberidai");
        setDescription("jp",
                "@E：あなたのエナゾーンにあるシグニが持つクラスが合計３種類以上ある場合、あなたのエナゾーンから緑のシグニを１枚まで対象とし、それを場に出す。\n" +
                "~#：【エナチャージ２】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Playground Slide, Second Play");
        setDescription("en",
                "@E: If there are 3 or more different classes among SIGNI in your ener zone, target up to 1 green SIGNI from your ener zone, and put it onto the field." +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "贰之游 滑梯");
        setDescription("zh_simplified", 
                "@E :你的能量区的精灵持有类别合计3种类以上的场合，从你的能量区把绿色的精灵1张最多作为对象，将其出场。" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
            if(CardDataSIGNIClass.getClassesCount(getCardsInEner(getOwner())) >= 3)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.GREEN).fromEner().playable()).get();
                putOnField(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
