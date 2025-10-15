package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;

public final class SPELL_R_ArkOfRobbery extends Card {

    public SPELL_R_ArkOfRobbery()
    {
        setImageSets("WXK01-038");

        setOriginalName("強奪の箱舟");
        setAltNames("ゴウダツノハコブネ Goudatsu no Hakobune");
        setDescription("jp",
                "ターン終了時まで、あなたのセンタールリグはあなたのすべての＜乗機＞のシグニに乗る。\n" +
                "ターン終了時まで、あなたのセンタールリグは@>@U：あなたのルリグアタックステップ開始時、あなたのシグニを好きな数対象とし、それらを場からトラッシュに置く。その後、この方法でトラッシュに置いたシグニ１体につき対戦相手の手札を１枚見ないで選び、捨てさせる。@@を得る。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Ark of Robbery");
        setDescription("en",
                "Until end of turn, your center LRIG rides all of your <<Riding Machine>> SIGNI.\n" +
                "Until end of turn, your center LRIG gains:" +
                "@>@U: At the beginning of your LRIG attack step, put any number of SIGNI from your field into the trash. Then, for each SIGNI put into the trash this way, choose 1 card from your opponent's hand without looking, and discard it.@@" +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "抢夺的箱舟");
        setDescription("zh_simplified", 
                "直到回合结束时为止，你的核心分身在你的全部的<<乗機>>精灵搭乘。\n" +
                "直到回合结束时为止，你的核心分身得到\n" +
                "@>@U :你的分身攻击步骤开始时，你的精灵任意数量作为对象，将这些从场上放置到废弃区。然后，依据这个方法放置到废弃区的精灵的数量，每有1只，就不看对战对手的手牌选1张，舍弃。@@" +
                "~#对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 3));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            getLRIG(getOwner()).getIndexedInstance().ride(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE).getExportedData());

            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachAbility(getLRIG(getOwner()), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().SIGNI());
            getAbility().getSourceCardIndex().getIndexedInstance().trash(data);
            
            if(getAbility().getSourceCardIndex().getIndexedInstance().trash(data) > 0)
            {
                int numTrashed = (int)data.stream().filter(cardIndex -> cardIndex.getLocation() == CardLocation.TRASH).count();
                if(numTrashed > 0)
                {
                    data = playerChoiceHand(numTrashed);
                    getAbility().getSourceCardIndex().getIndexedInstance().discard(data);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}
