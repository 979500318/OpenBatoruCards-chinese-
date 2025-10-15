package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_ApehuchiCrimsonAngel extends Card {
    
    public SIGNI_R1_ApehuchiCrimsonAngel()
    {
        setImageSets("WXDi-P02-056");
        
        setOriginalName("紅天　アペフチ");
        setAltNames("セイテンアペフチ Seiten Apefuchi");
        setDescription("jp",
                "@U：あなたの＜天使＞のシグニ１体がバニッシュされたとき、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。"
        );
        
        setName("en", "Kamuy - huci, Crimson Angel");
        setDescription("en",
                "@U: Whenever an <<Angel>> SIGNI on your field is vanished, target SIGNI on your field gets +5000 power until end of turn."
        );
        
        setName("en_fan", "Apehuchi, Crimson Angel");
        setDescription("en_fan",
                "@U: Whenever 1 of your <<Angel>> SIGNI is banished, target 1 of your SIGNI, and until end of turn, it gets +5000 power."
        );
        
		setName("zh_simplified", "红天 火婆");
        setDescription("zh_simplified", 
                "@U :当你的<<天使>>精灵1只被破坏时，你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return isOwnCard(cardIndex) && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ANGEL) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff(CardIndex cardIndex)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
    }
}
