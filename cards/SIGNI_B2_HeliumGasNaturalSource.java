package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_HeliumGasNaturalSource extends Card {
    
    public SIGNI_B2_HeliumGasNaturalSource()
    {
        setImageSets("WXDi-D06-013");
        
        setOriginalName("羅原　Ｈｅｇ");
        setAltNames("ラゲンヘリウムガス Ragen Heriumu Gasu Heg");
        setDescription("jp",
                "@U：このシグニがバトル以外によってバニッシュされたとき、対戦相手のルリグ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "He, Natural Element");
        setDescription("en",
                "@U: When this SIGNI is vanished outside of battle, down target LRIG on your opponent's field."
        );
        
        setName("en_fan", "Helium Gas, Natural Source");
        setDescription("en_fan",
                "@U: When this SIGNI is banished other than in battle, target 1 of your opponent's LRIGs, and down it."
        );
        
		setName("zh_simplified", "罗原 Heg");
        setDescription("zh_simplified", 
                "@U 当这只精灵因为战斗以外被破坏时，对战对手的分身1只作为对象，将其#D。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null || getEvent().getSourceCardIndex() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
