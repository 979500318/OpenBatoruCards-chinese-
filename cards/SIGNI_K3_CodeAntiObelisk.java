package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventAttach;

public final class SIGNI_K3_CodeAntiObelisk extends Card {
    
    public SIGNI_K3_CodeAntiObelisk()
    {
        setImageSets("WXDi-D07-019");
        
        setOriginalName("コードアンチ　オベリスク");
        setAltNames("コードアンチオベリスク Koodo Anchi Oberisuku");
        setDescription("jp",
                "@U：このシグニに[[ソウル]]が付いたとき、あなたか対戦相手のデッキの上からカードを２枚トラッシュに置く。\n" +
                "@A %K %R %X %X：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。その後、場に出たそれよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Obelisk, Code: Anti");
        setDescription("en",
                "@U: When a [[Soul]] is attached to this SIGNI, put the top two cards of your deck or your opponent's deck into their owner's trash.\n" +
                "@E %K %R %X %X: Put target SIGNI from your trash onto your field. Then, vanish target SIGNI on your opponent's field with power less than the SIGNI that entered the field."
        );
        
        setName("en_fan", "Code Anti Obelisk");
        setDescription("en_fan",
                "@U: Whenever a [[Soul]] is attached to this SIGNI, put the top 2 cards of your or your opponent's deck into the trash.\n" +
                "@A %K %R %X %X: Target 1 SIGNI from your trash, and put it onto the field. Then, target 1 of your opponent's SIGNI with power less than the one that entered the field, and banish it."
        );
        
		setName("zh_simplified", "古兵代号 方尖碑");
        setDescription("zh_simplified", 
                "@U :当这只精灵被[[灵魂]]附加时，从你或对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "@E %K%R%X %X:从你的废弃区把精灵1张作为对象，将其出场。然后，比出场的其的力量低的对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.RED, 1) + Cost.colorless(2)), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return EventAttach.getDataAttachUnderType() == CardUnderType.ATTACHED_SOUL ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 2);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash()).get();
            
            if(putOnField(target))
            {
                target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, target.getIndexedInstance().getPower().getValue()-1)).get();
                banish(target);
            }
        }
    }
}
