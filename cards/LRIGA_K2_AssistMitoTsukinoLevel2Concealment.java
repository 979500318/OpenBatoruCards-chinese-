package open.batoru.data.cards;

import open.batoru.data.DataTable;
import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionMillDeck;
import open.batoru.core.gameplay.actions.ActionRequestInfoDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_K2_AssistMitoTsukinoLevel2Concealment extends Card {

    public LRIGA_K2_AssistMitoTsukinoLevel2Concealment()
    {
        setImageSets("WXDi-CP01-023");

        setOriginalName("【アシスト】月ノ美兎　レベル２【隠蔽】");
        setAltNames("アシストツキノミトレベルニエイエン Ashisuto Tsukino Mito Reberu Ni Inpei Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@C：あなたのライフクロス１枚が対戦相手のシグニのアタックによってクラッシュされる場合、代わりにあなたのデッキの上からカードを５枚トラッシュに置いてもよい。@@を得る。"
        );

        setName("en", "[Assist] Mito, Level 2 [Concealment]");
        setDescription("en",
                "@E: This LRIG gains@>@C: If one of your Life Cloth is crushed by the attack of a SIGNI on your opponent's field, you may instead put the top five cards of your deck into your trash.@@until end of turn. "
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 2 [Concealment]");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@C: If 1 of your life cloth would be crushed by the attack of your opponent's SIGNI, you may put the top 5 cards of your deck into the trash instead."
        );

		setName("zh_simplified", "【支援】月之美兔 等级2【隐蔽】");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@C :你的生命护甲1张因为对战对手的精灵的攻击被击溃的场合，作为替代，可以从你的牌组上面把5张牌放置到废弃区。@@\n" +
                "。（牌组在4张以下的场合，不能置换）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
        }

        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.CRUSH, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler))
            );
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getDeckCount(getOwner()) >= 5 &&
                   event.getSourceAbility() == null && !isOwnCard(event.getSourceCardIndex()) && CardType.isSIGNI(event.getSource().getCardReference().getType());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionRequestInfoDeck(getOwner(), 5, DeckPosition.TOP));
            list.getAction(0).setOnActionCompleted(() ->
                ((ActionMillDeck)list.getAction(1)).setDataDeckInfo((DataTable<CardIndex>)list.getAction(0).getDataTable())
            );
            list.addAction(new ActionMillDeck(getOwner(), DeckPosition.TOP));
        }
    }
}
