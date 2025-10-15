package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_W3_LibraNaturalStarPrincess extends Card {
    
    public SIGNI_W3_LibraNaturalStarPrincess()
    {
        setImageSets("WXDi-P01-034");
        
        setOriginalName("羅星姫　リーブラー");
        setAltNames("ラセイキリーブラー Raseiki Riiburaa");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たないレベル１のシグニを４枚まで対象とし、それらをデッキに加えてシャッフルする。\n" +
                "@A $T1 %W：あなたのデッキの上からカードを４枚トラッシュに置く。この方法でレベル１のシグニが２枚以上トラッシュに置かれた場合、このターン、対戦相手のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );
        
        setName("en", "Libra, Natural Planet Queen");
        setDescription("en",
                "@E: Shuffle up to four target level one SIGNI without a #G from your trash into your deck.\n" +
                "@A $T1 %W: Put the top four cards of your deck into your trash. If two or more level one SIGNI were put into your trash this way, during this turn, if a SIGNI on your opponent's field is vanished, it is put into the trash instead of the Ener Zone."
        );
        
        setName("en_fan", "Libra, Natural Star Princess");
        setDescription("en_fan",
                "@E: Target up to 4 level 1 SIGNI without #G @[Guard]@ from your trash, and shuffle them into your deck.\n" +
                "@A $T1 %W: Put the top 4 cards of your deck into the trash. If 2 or more level 1 SIGNI were put into the trash this way, this turn, whenever your opponent's SIGNI is banished, it is put into the trash instead of the ener zone."
        );
        
		setName("zh_simplified", "罗天姬 天秤座");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G的等级1的精灵4张最多作为对象，将这些加入牌组洗切。\n" +
                "@A $T1 %W:从你的牌组上面把4张牌放置到废弃区。这个方法把等级1的精灵2张以上放置到废弃区的场合，这个回合，对战对手的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,4, new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            
            if(data.get() != null)
            {
                returnToDeck(data, DeckPosition.TOP);
                
                shuffleDeck();
            }
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = millDeck(4);
            
            if(new TargetFilter().own().SIGNI().withLevel(1).match(data).fromTrash().getValidTargetsCount() >= 2)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                    new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, dataRC ->
                        new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                    )
                );
                GFX.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, new GFXTextureCardCanvas("border/trash", 0.75,3)));
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}
