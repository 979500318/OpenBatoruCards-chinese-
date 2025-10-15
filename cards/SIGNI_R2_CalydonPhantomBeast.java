package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_CalydonPhantomBeast extends Card {
    
    public SIGNI_R2_CalydonPhantomBeast()
    {
        setImageSets("WXDi-P00-054");
        
        setOriginalName("幻獣　カリュドーン");
        setAltNames("ゲンジュウカリュドーン Genjuu Karyudoon");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、カードを１枚引く。\n" +
                "@E：あなたは手札を１枚捨てる。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Calydon, Phantom Terra Beast");
        setDescription("en",
                "@U: When this SIGNI is vanished, draw a card.\n" +
                "@E: Discard a card." +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Calydon, Phantom Beast");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, draw 1 card.\n" +
                "@E: You discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽 卡吕冬野猪");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，抽1张牌。\n" +
                "@E 你把手牌1张舍弃。（没有费用的@E能力不能选择不发动）" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            draw(1);
        }
        
        private void onEnterEff()
        {
            discard(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(cardIndex != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(cardIndex);
            }
        }
    }
}
