package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class LRIG_K1_MachinaOne extends Card {
    
    public LRIG_K1_MachinaOne()
    {
        setImageSets("WXDi-P04-014");
        
        setOriginalName("マキナ・ワン");
        setAltNames("マキナワン Makina Wan");
        setDescription("jp",
                "@C：このカードが[[ソウル]]として付いているシグニは@>@U $T1：このシグニがアタックしたとき、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。@@を得る。"
        );
        
        setName("en", "Machina One");
        setDescription("en",
                "@C: The SIGNI with this card attached to it as a [[Soul]] gains@>@U $T1: When this SIGNI attacks, add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Machina One");
        setDescription("en_fan",
                "@C: The SIGNI attached with this card as a [[Soul]] gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "玛琪娜·壹");
        setDescription("zh_simplified", 
                "@C :被这张牌作为[[灵魂]]附加的精灵得到\n" +
                "@>@U $T1 当这只精灵攻击时，从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT, CardLocation.CHEER);
            cont.setActiveUnderFlags(CardUnderType.ATTACHED_SOUL);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            return attachedAuto;
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
