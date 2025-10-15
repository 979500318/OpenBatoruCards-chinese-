package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_RuriruLonelyNaturalStone extends Card {

    public SIGNI_R1_RuriruLonelyNaturalStone()
    {
        setImageSets("WXDi-P07-062");

        setOriginalName("羅寂石　ルリル");
        setAltNames("ラジャクセキルリル Rajakuseki Ruriru");
        setDescription("jp",
                "@E：あなたの場に他の＜宝石＞のシグニがない場合、あなたは手札を１枚捨てる。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Ruriru, Lonely Natural Crystal");
        setDescription("en",
                "@E: If there are no other <<Jewel>> SIGNI on your field, discard a card." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );

        setName("en_fan", "Ruriru, Lonely Natural Stone");
        setDescription("en_fan",
                "@E: If you have no other <<Gem>> SIGNI on your field, discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "罗寂石 琉璃");
        setDescription("zh_simplified", 
                "@E :你的场上没有其他的<<宝石>>精灵的场合，你把手牌1张舍弃。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.GEM).except(getCardIndex()).getValidTargetsCount() == 0)
            {
                discard(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
