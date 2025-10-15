package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_SangaVOGUE3EXTREME extends Card {

    public LRIG_G3_SangaVOGUE3EXTREME()
    {
        setImageSets("SPDi43-16", "SPDi43-16P");

        setOriginalName("VOGUE3-EXTREME サンガ");
        setAltNames("ボーグスリーエクストリームサンガ Boogu Surii Ekusutoriimu Sanga");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニが持つ色が合計３種類以上ある場合、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それをエナゾーンに置く。\n" +
                "@A $G1 @[@|ブレイキング|@]@ %G0：あなたのルリグトラッシュから&E@0を持たないコストの合計が３以下の緑のアーツ１枚を対象とし、それをルリグデッキに加える。"
        );

        setName("en", "Sanga, VOGUE 3-EXTREME");
        setDescription("en",
                "@U: At the beginning of your attack phase,  if there are 3 or more colors among SIGNI on your field, target 1 SIGNI without #G @[Guard]@ from your trash, and put it into the ener zone.\n" +
                "@A $G1 @[@|Breaking|@]@ %G0: Target 1 green ARTS with a total cost of 3 or less and without &E@0 from your trash, and return it to the LRIG deck."
        );

		setName("zh_simplified", "VOGUE3-EXTREME 山河");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的精灵持有颜色合计3种类以上的场合，从你的废弃区把不持有#G的精灵1张作为对象，将其放置到能量区。\n" +
                "@A $G1 :霹雳%G0从你的分身废弃区把不持有&E]的费用的合计在3以下的绿色的必杀1张作为对象，将其加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Breaking");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getColorsCount(getSIGNIOnField(getOwner())) >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().not(new TargetFilter().guard()).fromTrash()).get();
                putInEner(target);
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().ARTS().withColor(CardColor.GREEN).withCost(0,3).
                not(new TargetFilter().withDescription("&E")).fromTrash(DeckType.LRIG)
            ).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
