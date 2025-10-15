package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class ARTS_WB_CrystalDust extends Card {

    public ARTS_WB_CrystalDust()
    {
        setImageSets("WX24-P4-002", "WX24-P4-002U");

        setOriginalName("クリスタル・ダスト");
        setAltNames("クリスタルダスト Kurisutaru Dasuto");
        setDescription("jp",
                "以下の３つを行う。\n" +
                "$$1対戦相手のすべてのルリグとシグニをダウンし凍結する。\n" +
                "$$2次の対戦相手のターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "$$3このターンと次のターンの間、能力を持たない対戦相手のシグニが場を離れる場合、代わりにトラッシュに置かれる。"
        );

        setName("en", "Crystal Dust");
        setDescription("en",
                "@[@|Do the following 3:|@]@\n" +
                "$$1 Down and freeze all of your opponent's LRIG and SIGNI.\n" +
                "$$2 Until the end of your opponent's next turn, all of your opponent's SIGNI lose their abilities.\n" +
                "$$3 During this turn and the next, if an opponent's SIGNI without abilities would leave the field, it is put into the trash instead."
        );

		setName("zh_simplified", "水晶·除灰");
        setDescription("zh_simplified", 
                "进行以下的3种。\n" +
                "$$1 对战对手的全部的分身和精灵#D并冻结。\n" +
                "$$2 直到下一个对战对手的回合结束时为止，对战对手的全部的精灵的能力失去。（这张必杀的使用后出场的精灵，不受这个效果的影响）\n" +
                "$$3 这个回合和下一个回合期间，不持有能力的对战对手的精灵离场的场合，作为替代，放置到废弃区。（这张必杀的使用后出场的精灵，也给予这个效果的影响）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> dataLRIG = getLRIGs(getOpponent());
            down(dataLRIG);
            freeze(dataLRIG);
            DataTable<CardIndex> dataSIGNI = getSIGNIOnField(getOpponent());
            down(dataSIGNI);
            freeze(dataSIGNI);
            
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOpponent()));
            
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().withoutAbilities(), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
            ));
            GFX.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, new GFXTextureCardCanvas("border/trash", 0.75,3)));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd().repeat(2));
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}
