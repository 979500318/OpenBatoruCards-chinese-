package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_B1_FubukiNemugaki extends Card {

    public SIGNI_B1_FubukiNemugaki()
    {
        setImageSets("WX25-CP1-064");

        setOriginalName("合歓垣フブキ");
        setAltNames("ネムガキフブキ Nemugaki Fubuki");
        setDescription("jp",
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000し、このシグニは@>@U：対戦相手のターン終了時、あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、対戦相手は手札を１枚捨てる。@@を得る。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "Nemugaki Fubuki");

        setName("en_fan", "Fubuki Nemugaki");
        setDescription("en",
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power, and it gains:" +
                "@>@U: At the end of your opponent's turn, reveal the top card of your deck. If it is a <<Blue Archive>> card, your opponent discards 1 card from their hand.@@" +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "合欢垣吹雪");
        setDescription("zh_simplified", 
                "@A 横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000，这只精灵得到\n" +
                "@>@U :对战对手的回合结束时，你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，对战对手把手牌1张舍弃。@@\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new DownCost(), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private ConditionState onAttachedAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE))
                {
                    discard(getOpponent(), 1);
                }
                
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}
