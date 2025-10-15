package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_G4_MidorikoGoldenGirlTypeFour extends Card {

    public LRIG_G4_MidorikoGoldenGirlTypeFour()
    {
        setImageSets("WXK01-003");

        setOriginalName("四型金娘　翠子");
        setAltNames("シガタキンキミドリコ Shigata Kinki Midoriko");
        setDescription("jp",
                "@C：あなたの中央のシグニゾーンにある＜怪異＞のシグニのパワーを＋2000する。\n" +
                "@E %G：あなたのルリグトラッシュから緑のアーツ１枚を対象とし、それをルリグデッキに加える。\n" +
                "@A $G1 @[@|テンタクル|@]@ #C：次の対戦相手のアタックフェイズ開始時、対戦相手のセンタールリグ１体とシグニ１体を対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Midoriko, Golden Girl Type Four");
        setDescription("en",
                "@C: The <<Apparition>> SIGNI in your center SIGNI zone gets +2000 power.\n" +
                "@E %G: Target 1 green ARTS from your LRIG trash, and add it to your LRIG deck.\n" +
                "@A $G1 @[@|Tentacle|@]@ #C: At the beginning of your opponent's next attack phase, target your opponent's center LRIG and 1 of their SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "四型金娘 翠子");
        setDescription("zh_simplified", 
                "@C :你的中央的精灵区的<<怪異>>精灵的力量+2000。\n" +
                "@E %G:从你的分身废弃区把绿色的必杀1张作为对象，将其加入分身牌组。\n" +
                "@A $G1 触手#C:下一个对战对手的攻击阶段开始时，对战对手的核心分身1只和精灵1只作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(4);
        setLimit(11);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.APPARITION).fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(2000));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);

            ActionAbility act = registerActionAbility(new CoinCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Tentacle");
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().ARTS().withColor(CardColor.GREEN).fromTrash(DeckType.LRIG)).get();
            returnToDeck(target, DeckPosition.TOP);
        }
        
        private void onActionEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextPhase(getOpponent(), GamePhase.ATTACK_PRE));
            
            CardLocation[] locations = new CardLocation[]{CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT,CardLocation.CHEER, CardLocation.LRIG};
            for(CardLocation location : locations) GFX.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOpponent(), location, "thorn_rose", 0, new int[]{50,205,50}));
            
            callDelayedEffect(record, () -> {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().LRIG()).get();
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

                target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            });
        }
    }
}
