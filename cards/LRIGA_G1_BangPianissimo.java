package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_BangPianissimo extends Card {
    
    public LRIGA_G1_BangPianissimo()
    {
        setImageSets("WXDi-P01-024");
        
        setOriginalName("バン＝ピアニッシモ");
        setAltNames("バンピアニッシモ Ban Pianisshimo");
        setDescription("jp",
                "@E：対戦相手のレベル３のシグニ１体を対象とし、それをバニッシュする。あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Bang =Pianissimo=");
        setDescription("en",
                "@E: Vanish target level three SIGNI on your opponent's field. Add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Bang-Pianissimo");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 3 SIGNI, and banish it. Target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "梆=轻柔地");
        setDescription("zh_simplified", 
                "@E :对战对手的等级3的精灵1只作为对象，将其破坏。从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3)).get();
            banish(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
