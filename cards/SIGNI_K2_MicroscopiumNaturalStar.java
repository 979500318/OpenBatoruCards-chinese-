package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K2_MicroscopiumNaturalStar extends Card {
    
    public SIGNI_K2_MicroscopiumNaturalStar()
    {
        setImageSets("WXDi-P06-082");
        
        setOriginalName("羅星　マイクロスコピウム");
        setAltNames("ラセイマイクロスコピウム Rasei Maikurosukopiumu");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。その後、この方法でレベル１のシグニがトラッシュに置かれた場合、%Xを支払ってもよい。そうした場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Microscopium, Natural Planet");
        setDescription("en",
                "@E: Put the top card of your deck into your trash. Then, if a level one SIGNI was put into your trash this way, you may pay %X. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Microscopium, Natural Star");
        setDescription("en_fan",
                "@E: Put the top card of your deck into the trash. Then, if a level 1 SIGNI was put into the trash this way, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "罗星 显微镜座");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到废弃区。然后，这个方法把等级1的精灵放置到废弃区的场合，可以支付%X。这样做的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。（力量在0以下的精灵因为规则被破坏）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getCardReference().getType()) && cardIndex.getIndexedInstance().getLevelByRef() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
