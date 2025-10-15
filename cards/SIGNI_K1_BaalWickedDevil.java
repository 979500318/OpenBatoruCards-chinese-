package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K1_BaalWickedDevil extends Card {
    
    public SIGNI_K1_BaalWickedDevil()
    {
        setImageSets("WXDi-P01-080");
        
        setOriginalName("凶魔　バアル");
        setAltNames("キョウマバアル Kyouma Baaru");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Baal, Doomed Evil");
        setDescription("en",
                "@E: Put the top three cards of your deck into your trash." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Baal, Wicked Devil");
        setDescription("en_fan",
                "@E: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "凶魔 巴力");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            millDeck(3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
