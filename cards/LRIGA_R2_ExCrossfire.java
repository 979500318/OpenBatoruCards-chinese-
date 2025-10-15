package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_R2_ExCrossfire extends Card {
    
    public LRIGA_R2_ExCrossfire()
    {
        setImageSets("WXDi-P04-020");
        
        setOriginalName("エクスクロスファイア");
        setAltNames("Ekusu Kurosufaia");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それのレベル１につき%Xを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E：対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを２枚まで対象とし、それらをトラッシュに置く。"
        );
        
        setName("en", "Ex Crossfire");
        setDescription("en",
                "@E: You may pay %X for each level of target SIGNI on your opponent's field. If you do, vanish it.\n" +
                "@E: Put up to two target cards in your opponent's Ener Zone that do not share a color with your opponent's center LRIG into their trash."
        );
        
        setName("en_fan", "Ex Crossfire");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and you may pay %X for each level it has. If you do, banish it.\n" +
                "@E: Target up to 2 cards from your opponent's ener zone that don't share a common color with your opponent's center LRIG, and put them into the trash."
        );
        
		setName("zh_simplified", "艾克斯交错火线");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就把%X:支付。这样做的场合，将其破坏。\n" +
                "@E :从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌2张最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(target.getIndexedInstance().getLevel().getValue())))
            {
                banish(target);
            }
        }
        
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())));
            trash(data);
        }
    }
}
