package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_K1_AlfouMemoriaWickedBeauty extends Card {

    public SIGNI_K1_AlfouMemoriaWickedBeauty()
    {
        setImageSets("WXDi-P10-072", "WXDi-P10-072P");

        setOriginalName("凶美　アルフォウ//メモリア");
        setAltNames("キョウビアルフォウメモリア Kyoubi Arufou Memoria");
        setDescription("jp",
                "@C：対戦相手のシグニは@>@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上のカードをトラッシュに置く。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Alfou//Memoria, Doomed Beauty");
        setDescription("en",
                "@C: SIGNI on your opponent's field gain@>@U: At the beginning of your attack phase, put the top card of your deck into your trash.@@" +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Alfou//Memoria, Wicked Beauty");
        setDescription("en_fan",
                "@C: Your opponent's SIGNI gain:" +
                "@>@U: At the beginning of your attack phase, put the top card of your deck into the trash.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "凶美 阿尔芙//回忆");
        setDescription("zh_simplified", 
                "@C :对战对手的精灵得到\n" +
                "@>@U :你的攻击阶段开始时，你的牌组最上面的牌放置到废弃区。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().OP().SIGNI(), new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getAbility().getSourceCardIndex().getIndexedInstance().isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            getAbility().getSourceCardIndex().getIndexedInstance().millDeck(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
