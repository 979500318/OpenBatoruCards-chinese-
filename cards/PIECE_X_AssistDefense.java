package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.GameEvent;

public final class PIECE_X_AssistDefense extends Card {

    public PIECE_X_AssistDefense()
    {
        setImageSets("WX24-P3-043");

        setOriginalName("アシスト・ディフェンス");
        setAltNames("アシストディフェンス Ashisuto Difensu");
        setDescription("jp",
                "このターン、あなたがダメージを受ける場合、代わりにあなたのレベル１以上のアップ状態のアシストルリグ２体をダウンしてもよい。"
        );

        setName("en", "Assist Defense");
        setDescription("en",
                "This turn, if you would be damaged, you may instead down 2 of your upped level 1 or higher Assist LRIG."
        );

		setName("zh_simplified", "支援·防御");
        setDescription("zh_simplified", 
                "这个回合，你受到伤害的场合，作为替代，可以把你的等级1以上的竖直状态的支援分身2只#D。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.ATTACK);
        setCost(Cost.colorless(2));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOwner(), ChronoDuration.turnEnd(), data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
            );
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return new TargetFilter().own().anyLRIG().except(CardLocation.LRIG).withLevel(1,0).upped().getValidTargetsCount() == 2;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            DataTable<CardIndex> data = new TargetFilter().own().anyLRIG().except(CardLocation.LRIG).withLevel(1,0).upped().getExportedData();
            list.addAction(new ActionDown(data.get(0)));
            list.addAction(new ActionDown(data.get(1)));
        }
    }
}

