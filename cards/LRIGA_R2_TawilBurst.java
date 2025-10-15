package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

import java.util.List;

public final class LRIGA_R2_TawilBurst extends Card {
    
    public LRIGA_R2_TawilBurst()
    {
        setImageSets("WXDi-P00-012");
        
        setOriginalName("タウィル＝バースト");
        setAltNames("タウィルバースト Tauiru Baasuto");
        setDescription("jp",
                "@E：対戦相手のシグニを、レベルの合計が対戦相手のセンタールリグのレベル以下になるように好きな枚対象とし、それらをバニッシュする。"
        );
        
        setName("en", "Tawil =Burst=");
        setDescription("en",
                "@E: Vanish any number of target SIGNI on your opponent's field with total level less than or equal to the level of your opponent's center LRIG."
        );
        
        setName("en_fan", "Tawil-Burst");
        setDescription("en_fan",
                "@E: Target any number of your opponent's SIGNI with total level equal to or lower than the level of your opponent's center LRIG, and banish them."
        );
        
		setName("zh_simplified", "塔维尔=迸发");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵、等级的合计在对战对手的核心分身的等级以下的任意数量作为对象，将这些破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.colorless(4));
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
            
            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0, getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue()), this::onEnterEffTargetCond);
            banish(data);
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).sum() <= getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue();
        }
    }
}
