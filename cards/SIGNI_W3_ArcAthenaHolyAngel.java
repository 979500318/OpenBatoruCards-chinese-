package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_ArcAthenaHolyAngel extends Card {
    
    public SIGNI_W3_ArcAthenaHolyAngel()
    {
        setImageSets("WXDi-D08-019");
        
        setOriginalName("聖天　アークアテナ");
        setAltNames("セイテンアークアテナ Seiten Aaku Atena");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜アーム＞のシグニがある場合、このシグニをアップする。\n" +
                "@A %W #D：対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Arc Athena, Holy Angel");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is an <<Armed>> SIGNI on your field, up this SIGNI.\n" +
                "@A %W #D: Return target SIGNI with power 8000 or less on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Arc Athena, Holy Angel");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is an <<Arm>> SIGNI on your field, up this SIGNI.\n" +
                "@A %W #D: Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand."
        );
        
		setName("zh_simplified", "圣天 大天使雅典娜");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有<<アーム>>精灵的场合，这只精灵竖直。\n" +
                "@A %W横置:对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.WHITE, 1)),
                new DownCost()
            ), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).getValidTargetsCount() > 0)
            {
                up();
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
        }
    }
}
