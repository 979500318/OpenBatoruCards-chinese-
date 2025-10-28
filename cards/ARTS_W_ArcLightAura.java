package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class ARTS_W_ArcLightAura extends Card {

    public ARTS_W_ArcLightAura()
    {
        setImageSets("WX24-D1-05", Mask.IGNORE+"SPDi37-13");

        setOriginalName("アーク・ライト・オーラ");
        setAltNames("アークライトオーラ Aaku Raito Oora");
        setDescription("jp",
                "あなたのルリグ１体を対象とし、ターン終了時まで、それは@>@U：このルリグがアタックしたとき、このルリグをアップし、ターン終了時まで、このルリグは能力を失う。@@を得る。\n" +
                "このターン、対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。"
        );

        setName("en", "Arc Light Aura");
        setDescription("en",
                "Target your LRIG, and until end of turn, it gains:" +
                "@>@U: When this LRIG attacks, up it, and until end of turn, it loses its abilities.@@" +
                "This turn, your opponent can't [[Guard]] unless they pay %X."
        );

        setName("zh_simplified", "弧光·光芒·圣气");
        setDescription("zh_simplified", 
                "你的分身1只作为对象，直到回合结束时为止，其得到" +
                "@>@U :当这只分身攻击时，这只分身竖直，直到回合结束时为止，这只分身的能力失去。@@" +
                "这个回合，对战对手如果不追加把%X支付，那么不能[[防御]]。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG()).get();
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            
            addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_GUARD, getOpponent(), ChronoDuration.turnEnd(), data -> new EnerCost(Cost.colorless(1)));
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            cardIndexSource.getIndexedInstance().up();
            cardIndexSource.getIndexedInstance().disableAllAbilities(cardIndexSource, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}

