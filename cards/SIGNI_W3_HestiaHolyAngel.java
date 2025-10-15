package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_HestiaHolyAngel extends Card {
    
    public SIGNI_W3_HestiaHolyAngel()
    {
        setImageSets("WXDi-P08-054");
        
        setOriginalName("聖天　ヘスチア");
        setAltNames("セイテンヘスチア Seiten Hesuchia");
        setDescription("jp",
                "@C：あなたが対戦相手のシグニかルリグのアタックによってダメージを受ける場合、代わりにこのシグニを場からトラッシュに置いてもよい。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Hestia, Blessed Angel");
        setDescription("en",
                "@C: If you would take damage by the attack of your opponent's SIGNI or LRIG, instead you may put this SIGNI on your field into its owner's trash." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Hestia, Holy Angel");
        setDescription("en_fan",
                "@C: If you would be damaged by the attack of your opponent's SIGNI or LRIG, you may put this SIGNI from your field into the trash instead." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "圣天 赫斯提亚");
        setDescription("zh_simplified", 
                "@C :你因为对战对手的精灵或分身的攻击受到伤害的场合，作为替代，可以把这只精灵从场上放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data -> 
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL,OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == sourceAbilityRC.getAbilityOwner() &&
                     event.getSourceAbility() == null && !isOwnCard(event.getSourceCardIndex());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(getCardIndex()));
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
